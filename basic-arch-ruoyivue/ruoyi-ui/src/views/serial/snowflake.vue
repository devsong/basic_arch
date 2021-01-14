<template>
  <div>
    <el-form
      :inline="true"
      :model="snowflakeQueryForm"
      :rules="rules"
      label-width="120px"
      ref="snowflakeQueryForm"
    >
      <el-form-item label="序列号" prop="id">
        <el-input
          @keyup.enter.native="handleSnowflakeQuery"
          clearable
          placeholder="请输入序列号"
          size="small"
          style="width: 240px"
          v-model="snowflakeQueryForm.id"
        />
      </el-form-item>

      <el-form-item>
        <el-button @click="handleSnowflakeQuery" icon="el-icon-search" size="mini" type="primary">查询</el-button>
      </el-form-item>
    </el-form>

    <el-form
      :inline="true"
      :model="snowflakeDecodeForm"
      label-width="120px"
      ref="snowflakeDecodeForm"
    >
      <el-form-item :visible.sync="snowflakeDecodeOpen" label-width="120px">
        <el-col :span="24">
          <el-form-item label="时间戳：" prop="timestamp">{{ snowflakeDecodeForm.timestamp }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="时间(易读格式)：" prop="time">{{ snowflakeDecodeForm.time }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="数据中心：" prop="dataCenterId">{{ snowflakeDecodeForm.dataCenterId }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="机器ID：" prop="workerId">{{ snowflakeDecodeForm.workerId }}</el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="随机序列号：" prop="seq">{{ snowflakeDecodeForm.seq }}</el-form-item>
        </el-col>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getSnowflake, decode } from '@/api/serial/index';

export default {
  name: 'Snowflake',
  data() {
    return {
      snowflakeQueryForm: {
        id: undefined
      },
      snowflakeDecodeOpen: false,
      snowflakeDecodeForm: {
        timestamp: 0,
        time: '',
        dataCenterId: 0,
        workerId: 0,
        seq: 0
      },
      rules: {
        id: [
          {
            required: true,
            message: 'snowflake流水号不能为空',
            trigger: 'blur'
          },
          {
            pattern: /^[0-9]*$/,
            message: '请输入正确的snowflake流水号',
            trigger: 'blur'
          }
        ]
      }
    };
  },
  created() {
    this.reset();
  },
  methods: {
    // 解码snowflake流水号
    handleSnowflakeQuery() {
      this.$refs['snowflakeQueryForm'].validate(valid => {
        if (valid) {
          decode(this.snowflakeQueryForm).then(response => {
            this.snowflakeDecodeForm = response.data;
          });
        }
      });
    },
    reset() {
      this.resetForm('snowflakeQueryForm');
      this.resetForm('snowflakeDecodeForm');
      // 获取snowflake流水号
      getSnowflake().then(response => {
        this.snowflakeQueryForm.id = response.data;
        // 解码流水号
        decode(this.snowflakeQueryForm).then(response => {
          this.snowflakeDecodeForm = response.data;
        });
      });
    }
  }
};
</script>