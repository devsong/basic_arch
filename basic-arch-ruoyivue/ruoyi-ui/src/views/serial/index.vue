<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="业务key" prop="title">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入业务key"
          clearable
          style="width: 240px;"
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作时间">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['serial:segment:update']"
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          v-hasPermi="['serial:segment:list']"
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="业务KEY" align="center" prop="key" />
      <el-table-column label="已使用ID" align="center" prop="maxId" />
      <el-table-column label="步长" align="center" prop="step" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="描述信息" align="center" prop="description" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['serial:segment:update']"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleForbidden(scope.row,scope.index)"
          >禁用</el-button>
        </template>

        <template slot-scope="scope">
          <el-button
            v-hasPermi="['serial:segment:update']"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDelete(scope.row,scope.index)"
          >删除</el-button>
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

    <!-- 新增序列号 -->
    <!--
    <el-dialog title="新增序列号" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="业务key：">{{ form.bizKey }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="maxId：">{{ form.maxId }}</el-form-item>
            <el-form-item label="步长：">{{ form.step }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述：">{{ form.method }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：">
              <div v-if="form.status === 0">正常</div>
              <div v-else-if="form.status === 1">禁用</div>
              <div v-else-if="form.status === 2">删除</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建时间：">{{ parseTime(form.createTime) }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
    -->

    <!-- 序列号解码 -->
    <!--
    <el-dialog title="解码序列号" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="snowflakeForm" :model="snowflakeForm" :inline="true" label-width="68px">
        <el-form-item label="序列号" prop="id">
          <el-input
            v-model="snowflakeForm.id"
            placeholder="请输入序列号"
            clearable
            style="width: 240px;"
            size="small"
            @keyup.enter.native="handleSnowflakeQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSnowflakeQuery">查询</el-button>
        </el-form-item>
      </el-form>

      <el-form ref="snowflakeForm" :model="snowflakeForm" label-width="100px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="时间：">{{ snowflakeForm.time }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据中心：">{{ snowflakeForm.dataCenterId }}</el-form-item>
            <el-form-item label="机器ID：">{{ snowflakeForm.workerId }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="随机序列号：">{{ snowflakeForm.seq }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
    -->
  </div>
</template>

<script>
import { list, add, forbidden, remove, decode, exportBizKey } from '@/api/serial/index';

export default {
  name: 'Serial',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
        name: undefined
      },
      snowflakeForm: {
        id: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询登录日志 */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.list = response.data;
        this.total = response.page.total;
        this.loading = false;
      }
      );
    },
    // 操作日志状态字典翻译
    // statusFormat(row, column) {
    //   return this.selectDictLabel(this.statusOptions, row.status);
    // },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // handleSnowflakeQuery(){
    //     decode(snowflakeForm.id);
    // }
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm('queryForm');
      this.handleQuery();
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.operId);
      this.multiple = !selection.length;
    },

    handleAdd() {
      add();
    },

    /** 禁用 */
    handleForbidden(row) {
      const bizKey = row.key || this.ids;
      this.$confirm('是否确认禁用"' + bizKey + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return forbidden(bizKey);
      }).then(() => {
        this.getList();
        this.msgSuccess('禁用成功');
      }).catch(function() {});
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const operIds = row.operId || this.ids;
      this.$confirm('是否确认删除"' + operIds + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return remove(operIds);
      }).then(() => {
        this.getList();
        this.msgSuccess('删除成功');
      }).catch(function() {});
    },

    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有业务key数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportBizKey(queryParams);
      }).then(response => {
        this.download(response.msg);
      }).catch(function() {});
    }
  }
};
</script>

