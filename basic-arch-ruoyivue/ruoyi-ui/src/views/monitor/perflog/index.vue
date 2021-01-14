<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="产品线" prop="product">
        <el-select
          filterable
          v-model="queryParams.product"
          placeholder="请选择"
          @change="handleProductChange"
        >
          <el-option v-for="item in option.products" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="分组" prop="group">
        <el-select
          filterable
          v-model="queryParams.group"
          placeholder="请选择"
          @change="handleGroupChange"
        >
          <el-option v-for="item in option.groups" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="应用" prop="app">
        <el-select filterable v-model="queryParams.app" placeholder="请选择" @change="handleAppChange">
          <el-option v-for="item in option.apps" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="类名" prop="clazz">
        <el-select
          filterable
          v-model="queryParams.clazz"
          placeholder="请选择"
          @change="handleClazzChange"
        >
          <el-option v-for="item in option.clazzs" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="方法名" prop="method">
        <el-select
          filterable
          v-model="queryParams.method"
          placeholder="请选择"
          @change="handleMethodChange"
        >
          <el-option v-for="item in option.methods" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="服务IP" prop="operateIp">
        <el-select filterable v-model="queryParams.operateIp" placeholder="请选择">
          <el-option v-for="item in option.operateIps" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>

      <el-form-item label="时间" prop="createTime">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 320px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>

      <el-form-item label="日志ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入日志ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="loading"
      class="cell-limit"
      :data="logList"
      :cell-class-name="tableCellClassName"
      height="500"
      @selection-change="handleSelectionChange"
      @cell-dblclick="handleCellDbClick"
    >
      <el-table-column type="selection" align="center" width="55" />
      <el-table-column fixed="left" label="日志ID" align="center" width="180" prop="id" />
      <el-table-column label="产品线" align="center" prop="product" />
      <el-table-column label="服务分组" align="center" prop="groupName" />
      <el-table-column label="应用名" align="center" width="120" prop="app" />
      <el-table-column
        label="类名"
        align="center"
        width="180"
        prop="clazz"
        title="clazz"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="方法名" align="center" width="80" prop="method" />
      <el-table-column label="操作IP" align="center" width="120" prop="operatorIp" />
      <el-table-column label="执行时间" align="center" prop="executeTimespan" />
      <el-table-column
        label="入参"
        align="center"
        prop="paramsIn"
        title="paramsIn"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        label="出参"
        align="center"
        prop="paramsOut"
        title="paramsOut"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="状态码" align="center" prop="code" />
      <el-table-column label="异常信息" align="center" prop="errmsg" />
      <el-table-column fixed="right" label="创建时间" align="center" prop="createTime" width="180" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改系统接口日志对话框 -->
    <el-dialog title="明细" :visible.sync="open" width="700px" height="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">{{ detailMsg }}</el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style lang="scss" scope>
.cell-limit tr td .cell {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2; /*可以显示的行数，超出部分用...表示 */
  -webkit-box-orient: vertical;
}
</style>

<script>
import { listLog, getLog, delLog, addLog, updateLog, exportLog, getMetaLog } from '@/api/monitor/perflog';

export default {
  name: 'Sysperflog',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 系统接口日志表格数据
      logList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 下拉列表数据
      option: {
        // 产品线
        products: [],
        // 分组
        groups: [],
        // 应用
        apps: [],
        // 类名
        clazzs: [],
        // 方法名
        methods: [],
        // 服务器IP
        operateIps: []
      },
      // 时间筛选
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        product: undefined,
        group: undefined,
        app: undefined,
        clazz: undefined,
        method: undefined,
        operateIp: undefined,
        level: undefined
      },
      // 表单参数
      form: {},
      // 明细日志
      detailMsg: '',
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getProducts();
    this.getList();
  },
  methods: {
    /** 查询系统接口日志列表 */
    getList() {
      this.loading = true;
      listLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.logList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.detailMsg = '';
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        metaId: undefined,
        executeTimespan: undefined,
        paramsIn: undefined,
        paramsOut: undefined,
        code: undefined,
        errmsg: undefined,
        createTime: undefined,
        timestamp: undefined
      };
      this.resetForm('form');
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm');
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = '添加系统接口日志';
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getLog(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = '修改系统接口日志';
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateLog(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess('修改成功');
                this.open = false;
                this.getList();
              }
            });
          } else {
            addLog(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess('新增成功');
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
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除系统接口日志编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delLog(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess('删除成功');
      }).catch(function () { });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有系统接口日志数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return exportLog(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function () { });
    },

    //
    tableCellClassName({ row, column, rowIndex, columnIndex }) {
      row.index = rowIndex;
      column.index = columnIndex;
    },

    handleCellDbClick(row, column, cell, event) {
      // 5 9 10列的数据较长,需要弹出对话框显示
      const index = column.index;
      if (index === 5 || index === 9 || index === 10) {
        JSON.stringify(cell);
        this.open = true;
        this.detailMsg = cell.innerText;
      }
    },

    // 获取产品线
    getProducts() {
      const query = {
        level: 1
      };
      getMetaLog(query).then(response => {
        this.option.products = response.data;
        this.option.products.push('*');
      });
    },

    // 产品线
    handleProductChange() {
      const query = {
        level: 2,
        product: this.queryParams.product
      };
      getMetaLog(query).then(response => {
        this.option.groups = response.data;
        this.option.groups.push('*');
      });
    },

    // 服务组
    handleGroupChange() {
      const query = {
        level: 3,
        product: this.queryParams.product,
        group: this.queryParams.group
      };
      getMetaLog(query).then(response => {
        this.option.apps = response.data;
        this.option.apps.push('*');
      });
    },

    // 应用
    handleAppChange() {
      const query = {
        level: 4,
        product: this.queryParams.product,
        group: this.queryParams.group,
        app: this.queryParams.app
      };
      getMetaLog(query).then(response => {
        this.option.clazzs = response.data;
        this.option.clazzs.push('*');
      });
    },

    // 方法
    handleClazzChange() {
      this.queryParams.level = 5;
      const query = {
        level: 5,
        product: this.queryParams.product,
        group: this.queryParams.group,
        app: this.queryParams.app,
        clazz: this.queryParams.clazz
      };
      getMetaLog(query).then(response => {
        this.option.methods = response.data;
        this.option.methods.push('*');
      });
    },

    // ip
    handleMethodChange() {
      const query = {
        level: 6,
        product: this.queryParams.product,
        group: this.queryParams.group,
        app: this.queryParams.app,
        method: this.queryParams.method
      };
      getMetaLog(query).then(response => {
        this.option.operateIps = response.data;
        this.option.operateIps.push('*');
      });
    },

  }
};
</script>
