<template>
  <el-form :inline="true" :model="base62Form"  label-width="120px" ref="base62Form">
    <el-row>
      <el-col :span="24">
        <el-form-item label="编码值" prop="id">
          <el-input
            @keyup.enter.native="handleDecodeFor62"
            clearable
            placeholder="请输入编码值"
            size="small"
            style="width: 240px"
            v-model="base62Form.id"
          />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="结果：" prop="result">{{ base62Form.result }}</el-form-item>
      </el-col>

      <el-col :span="8" :offset="4">
        <el-form-item>
          <el-button
            @click="handleEncodeFor62"
            icon="el-icon-search"
            size="mini"
            type="primary"
          >BASE62编码</el-button>
        </el-form-item>
      </el-col>

      <el-col :span="8">
        <el-form-item>
          <el-button
            @click="handleDecodeFor62"
            icon="el-icon-search"
            size="mini"
            type="primary"
          >BASE62解码</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
import { base62encode, base62decode } from '@/api/serial/index';
export default {
  name: 'Base62',
  data() {
    return {
      base62Form: {
        id: undefined,
        result: ''
      }
    };
  },
  created() {
    this.reset();
  },
  methods: {
    reset() {
      this.resetForm("base62Form");
    },
    // base62编码
    handleEncodeFor62() {
      const id = this.base62Form.id;
      base62encode(id).then(response => {
        this.base62Form.result = response.data;
      });
    },

    // base62解码
    handleDecodeFor62() {
      const id = this.base62Form.id;
      base62decode(id).then(response => {
        this.base62Form.result = response.data;
      });
    }
  }
};
</script>