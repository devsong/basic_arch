<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">

      <el-form-item label="产品线" prop="product">
        <el-select v-model="queryParams.product" placeholder="请选择" change="handleProductChange">
          <el-option
            v-for="item in option.products"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="分组" prop="group">
        <el-select v-model="queryParams.group" placeholder="请选择" change="handleGroupChange">
          <el-option
            v-for="item in option.groups"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="应用" prop="app">
        <el-select v-model="queryParams.app" placeholder="请选择" change="handleAppChange">
          <el-option
            v-for="item in option.apps"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="类名" prop="clazz">
        <el-select v-model="queryParams.clazz" placeholder="请选择" change="handleClazzChange">
          <el-option
            v-for="item in option.clazzs"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="方法名" prop="method">
        <el-select v-model="queryParams.method" placeholder="请选择" change="handleMethodChange">
          <el-option
            v-for="item in option.methods"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="服务IP" prop="operateIp">
        <el-select v-model="queryParams.operateIp" placeholder="请选择" change="handleOperateIpChange">
          <el-option
            v-for="item in option.operateIps"
            :key="item"
            :label="item"
            :value="item"
          />
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
      height="500"
      @selection-change="handleSelectionChange"
      @cell-dblclick="handleCellDbClick"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="日志ID" align="center" prop="id" />
      <el-table-column label="元数据id" align="center" prop="metaId" />
      <el-table-column label="执行时间" align="center" prop="executeTimespan" />
      <el-table-column label="入参" align="center" prop="paramsIn" />
      <el-table-column label="出参" align="center" prop="paramsOut" />
      <el-table-column label="状态码" align="center" prop="code" />
      <el-table-column label="异常信息" align="center" prop="errmsg" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改系统接口日志对话框 -->
    <el-dialog title="明细" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="业务key：" prop="key">
              {{ detailMsg }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style lang="scss">
  .cell-limit tr td .cell{
    overflow : hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;      /*可以显示的行数，超出部分用...表示 */
    -webkit-box-orient: vertical;
  }
</style>

<script>
import { listLog, getLog, delLog, addLog, updateLog, exportLog } from '@/api/monitor/perflog';

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
        operateIp: undefined
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
      this.reset();
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
    submitForm: function() {
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
      }).then(function() {
        return delLog(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess('删除成功');
      }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有系统接口日志数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportLog(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function() {});
    },
    handleCellDbClick(row, column, cell, event) {
      // 5 6 8列的数据较长,需要弹出对话框显示
      if (column === 5 || column === 6 || column === 8) {
        console.log(JSON.stringify(cell));
        this.open = true;
        // this.detailMsg = cell.text;
      }
    }
  }
};
</script>
