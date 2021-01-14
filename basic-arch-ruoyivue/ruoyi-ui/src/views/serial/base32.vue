<template>
  <el-form :inline="true" :model="base32Form" label-width="120px" ref="base32Form">
    <el-row>
      <el-col :span="24">
        <el-form-item label="编码值" prop="id">
          <el-input
            @keyup.enter.native="handleEncodeFor32"
            clearable
            placeholder="请输入编码值"
            size="small"
            style="width: 240px"
            v-model="base32Form.id"
          />
        </el-form-item>
      </el-col>

      <el-col :span="24">
        <el-form-item label="结果：" prop="result">{{ base32Form.result }}</el-form-item>
      </el-col>

      <el-col :span="8" :offset="4">
        <el-form-item>
          <el-button
            @click="handleEncodeFor32"
            icon="el-icon-search"
            size="mini"
            type="primary"
          >BASE32编码</el-button>
        </el-form-item>
      </el-col>

      <el-col :span="8">
        <el-form-item>
          <el-button
            @click="handleDecodeFor32"
            icon="el-icon-search"
            size="mini"
            type="primary"
          >BASE32解码</el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
import { base32encode, base32decode } from '@/api/serial/index';
export default {
  name: 'Base32',
  data() {
    return {
      base32Form: {
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
      this.resetForm("base32Form");
    },
    // base32编码
    handleEncodeFor32() {
      const id = this.base32Form.id;
      base32encode(id).then(response => {
        this.base32Form.result = response.data;
      });
    },

    // base32解码
    handleDecodeFor32() {
      const id = this.base32Form.id;
      base32decode(id).then(response => {
        this.base32Form.result = response.data;
      });
    }
  }
};
</script>