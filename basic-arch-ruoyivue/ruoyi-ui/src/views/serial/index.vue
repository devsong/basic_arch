<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" :inline="true" label-width="68px">
      <el-form-item label="业务key" prop="name">
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
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="描述信息" align="center" prop="description" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['serial:segment:update']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >编辑</el-button>

          <el-button
            v-hasPermi="['serial:segment:update']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
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
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="24">
            <el-form-item label="业务key：" prop="key">
              <el-input v-model="form.key" placeholder="请输入业务key" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="maxId：" prop="maxId">
              <el-input v-model="form.maxId" placeholder="请输入MaxId" />
            </el-form-item>
            <el-form-item label="步长：">
              <el-input v-model="form.step" placeholder="请输入步长" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述：">
              <el-input v-model="form.description" placeholder="请输入描述" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>
    <!-- -->

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
import { list, getBizKey, addSegment, updateSegment, changeSegmentStatus, exportBizKey, decode, getSegmentKey, getSnowflake } from '@/api/serial/index';

export default {
  name: 'Serial',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 添加OR编辑
      addOrEdit: 'add',
      // 弹出层标题
      title: '',
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
        name: undefined
      },
      snowflakeForm: {
        id: undefined
      }
    };
  },
  created() {
    this.getList();
    this.getDicts('sys_normal_disable').then(response => {
      this.statusOptions = response.data;
    });
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
      this.keys = selection.map(item => item.key);
      this.multiple = !selection.length;
    },

    handleStatusChange(row) {
      const text = row.status === '0' ? '启用' : '停用';
      this.$confirm('确认要' + text + '"' + row.key + '"的业务key?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return changeSegmentStatus(row.key, row.status);
      }).then(() => {
        this.msgSuccess(text + '成功');
      }).catch(function() {
        row.status = row.status === '0' ? '1' : '0';
      });
    },

    // 新增
    handleAdd() {
      this.open = true;
      this.addOrEdit = 'add';
      this.resetForm('form');
      this.title = '添加序列号';
    },

    // 编辑
    handleUpdate(row) {
      this.resetForm('form');
      this.addOrEdit = 'edit';
      const key = row.key || this.keys;
      getBizKey(key).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = '修改序列号';
      });
    },

    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.addOrEdit === 'edit') {
            updateSegment(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess('修改成功');
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSegment(this.form).then(response => {
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
      const key = row.key || this.keys;
      this.$confirm('是否确认删除"' + key + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return changeSegmentStatus(key, 2);
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

