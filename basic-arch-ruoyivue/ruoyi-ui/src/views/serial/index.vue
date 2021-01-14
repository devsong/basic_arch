/* eslint-disable eqeqeq */
<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryParams" label-width="68px" ref="queryForm">
      <el-form-item label="业务key" prop="name">
        <el-input
          @keyup.enter.native="handleQuery"
          clearable
          placeholder="请输入业务key"
          size="small"
          style="width: 240px"
          v-model="queryParams.name"
        />
      </el-form-item>

      <el-form-item label="操作时间">
        <el-date-picker
          end-placeholder="结束日期"
          range-separator="-"
          size="small"
          start-placeholder="开始日期"
          style="width: 240px"
          type="daterange"
          v-model="dateRange"
          value-format="yyyy-MM-dd"
        />
      </el-form-item>

      <el-form-item>
        <el-button @click="handleQuery" icon="el-icon-search" size="mini" type="primary">搜索</el-button>
        <el-button @click="resetQuery" icon="el-icon-refresh" size="mini">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          @click="handleAdd"
          icon="el-icon-plus"
          size="mini"
          type="primary"
          v-hasPermi="['serial:segment:update']"
        >新增</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button @click="openSnowflake" icon="el-icon-info" size="mini" type="primary">snowflake</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button @click="openBase32" icon="el-icon-info" size="mini" type="primary">BASE32编解码</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button @click="openBase62" icon="el-icon-info" size="mini" type="primary">BASE62编解码</el-button>
      </el-col>
    </el-row>

    <el-table :data="list" @selection-change="handleSelectionChange" v-loading="loading">
      <el-table-column align="center" type="selection" width="55" />
      <el-table-column align="center" label="业务KEY" prop="key" />
      <el-table-column align="center" label="已使用ID" prop="maxId" />
      <el-table-column align="center" label="步长" prop="step" />
      <el-table-column align="center" label="随机数" prop="randomLen" />
      <el-table-column align="center" label="状态" prop="status">
        <template slot-scope="scope">
          <el-switch
            :active-value="0"
            :inactive-value="1"
            @change="handleStatusChange(scope.row)"
            v-model="scope.row.status"
          />
        </template>
      </el-table-column>
      <el-table-column align="center" label="描述信息" prop="description" />
      <el-table-column
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
        label="操作"
        width="180"
      >
        <template slot-scope="scope">
          <el-button
            @click="handleGet(scope.row)"
            icon="el-icon-search"
            size="mini"
            type="text"
            v-hasPermi="['serial:segment:list']"
          >获取</el-button>

          <el-button
            @click="handleUpdate(scope.row)"
            icon="el-icon-edit"
            size="mini"
            type="text"
            v-hasPermi="['serial:segment:update']"
          >编辑</el-button>

          <el-button
            @click="handleDelete(scope.row)"
            icon="el-icon-delete"
            size="mini"
            type="text"
            v-hasPermi="['serial:segment:update']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
      v-show="total > 0"
    />

    <!-- 新增序列号 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="700px">
      <el-form :model="form" label-width="100px" ref="form" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="业务key：" prop="key">
              <el-input placeholder="请输入业务key" v-model="form.key" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="maxId：" prop="maxId">
              <el-input placeholder="请输入MaxId" v-model="form.maxId" />
            </el-form-item>
            <el-form-item label="步长：" prop="step">
              <el-input placeholder="请输入步长" v-model="form.step" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="尾部随机数位数：" prop="randomLen">
              <el-input placeholder="尾部随机数位数" v-model="form.randomLen" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述：" prop="description">
              <el-input placeholder="请输入描述" v-model="form.description" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：" prop="status">
              <template>
                <el-switch :active-value="0" :inactive-value="1" v-model="form.status" />
              </template>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="dialog-footer" slot="footer">
        <el-button @click="submitForm" type="primary">确 定</el-button>
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog append-to-body title="解码序列号" width="500px" :visible.sync="snowflakeFlag" @close="closeSnowflake">
      <snowflake-dialog ref="snowflakeDialog"></snowflake-dialog>
    </el-dialog>

    <el-dialog append-to-body title="编解码Base32" width="500px" :visible.sync="base32Flag" @close="closeBase32Dialog">
      <base32-dialog ref="base32Dialog"></base32-dialog>
    </el-dialog>

    <el-dialog append-to-body title="编解码Base62" width="500px" :visible.sync="base62Flag" @close="closeBase62Dialog">
      <base62-dialog ref="base62Dialog"></base62-dialog>
    </el-dialog>
  </div>
</template>

<script>
import { list, getBizKey, addSegment, updateSegment, changeSegmentStatus, getSegmentKey } from "@/api/serial/index";
import Base32Dialog from './base32';
import Base62Dialog from './base62';
import SnowflakeDialog from './snowflake';
export default {
  name: "Serial",
  components: {
    Base32Dialog, Base62Dialog, SnowflakeDialog
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 添加OR编辑
      addOrEdit: "add",
      // 弹出层标题
      title: "",
      // 选中数组
      keys: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 是否显示弹出层
      open: false,
      // 类型数据字典
      typeOptions: [],
      // 类型数据字典
      statusOptions: [],
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
      },
      snowflakeFlag: false,
      base32Flag: false,
      base62Flag: false,
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.list = response.data;
          this.total = response.page.total;
          this.loading = false;
        }
      );
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.keys = selection.map((item) => item.key);
      this.multiple = !selection.length;
    },

    handleStatusChange(row) {
      const text = row.status == "0" ? "启用" : "停用";
      this.$confirm("确认要" + text + '"' + row.key + '"的业务key?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return changeSegmentStatus(row.key, row.status);
      }).then(() => {
        this.msgSuccess(text + "成功");
        this.getList();
      }).catch(function () {
        row.status = row.status == "0" ? "1" : "0";
      });
    },

    // 获取
    handleGet(row) {
      const key = row.key || this.keys;
      getSegmentKey(key).then((response) => {
        this.msgSuccess(response.data);
      });
    },

    // 新增
    handleAdd() {
      this.addOrEdit = "add";
      this.resetForm("form");
      this.title = "添加序列号";
      this.open = true;
    },

    // 编辑
    handleUpdate(row) {
      this.resetForm("form");
      this.addOrEdit = "edit";
      const key = row.key || this.keys;
      getBizKey(key).then((response) => {
        this.form = response.data;
        this.title = "修改序列号";
        this.open = true;
      });
    },

    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.addOrEdit == "edit") {
            updateSegment(this.form).then((response) => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSegment(this.form).then((response) => {
              if (response.code == 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const key = row.key || this.keys;
      this.$confirm('是否确认删除"' + key + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        return changeSegmentStatus(key, 2);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function () { });
    },

    /**打开snowflake对话框 */
    openSnowflake() {
      this.snowflakeFlag = true;
    },

    closeSnowflake(){
      this.snowflakeFlag = false;
      this.$refs.snowflakeDialog.reset();
    },

    /**打开base32对话框 */
    openBase32() {
      this.base32Flag = true;
    },

    closeBase32Dialog(){
      this.base32Flag = false;
      this.$refs.base32Dialog.reset();
    },

    /**打开base62对话框 */
    openBase62() {
      this.base62Flag = true;
    },

    closeBase62Dialog(){
      this.base62Flag = false;
      this.$refs.base62Dialog.reset();
    },

  },
};
</script>
