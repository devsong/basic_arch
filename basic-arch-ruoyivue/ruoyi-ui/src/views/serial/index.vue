/* eslint-disable eqeqeq */
<template>
  <div class="app-container">
    <el-form
      ref="queryForm"
      :model="queryParams"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="业务key" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入业务key"
          clearable
          style="width: 240px"
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
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          >新增
        </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-info"
          size="mini"
          @click="handleSnowflakeDialog"
          >snowflake
        </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-info"
          size="mini"
          @click="base32Flag = true"
          >BASE32编解码</el-button
        >
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-info"
          size="mini"
          @click="base62Flag = true"
          >BASE62编解码</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="list"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="业务KEY" align="center" prop="key" />
      <el-table-column label="已使用ID" align="center" prop="maxId" />
      <el-table-column label="步长" align="center" prop="step" />
      <el-table-column label="随机数" align="center" prop="randomLen" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="描述信息" align="center" prop="description" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['serial:segment:list']"
            size="mini"
            type="text"
            icon="el-icon-search"
            @click="handleGet(scope.row)"
            >获取
          </el-button>
          <el-button
            v-hasPermi="['serial:segment:update']"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >编辑
          </el-button>

          <el-button
            v-hasPermi="['serial:segment:update']"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
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
            <el-form-item label="步长：" prop="step">
              <el-input v-model="form.step" placeholder="请输入步长" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="尾部随机数位数：" prop="randomLen">
              <el-input v-model="form.randomLen" placeholder="尾部随机数位数" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="描述：" prop="description">
              <el-input v-model="form.description" placeholder="请输入描述" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态：" prop="status">
              <template>
                <el-switch
                  v-model="form.status"
                  :active-value="0"
                  :inactive-value="1"
                />
              </template>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 序列号解码 -->
    <el-dialog
      title="解码序列号"
      :visible.sync="snowflakeOpen"
      width="500px"
      append-to-body
    >
      <el-form
        ref="snowflakeQueryForm"
        :model="snowflakeQueryForm"
        :inline="true"
        :rules="rules"
        label-width="120px"
      >
        <el-form-item label="序列号" prop="id">
          <el-input
            v-model="snowflakeQueryForm.id"
            placeholder="请输入序列号"
            clearable
            style="width: 240px"
            size="small"
            @keyup.enter.native="handleSnowflakeQuery"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleSnowflakeQuery"
            >查询</el-button
          >
        </el-form-item>

        <el-form-item :visible.sync="snowflakeDecodeOpen" label-width="120px">
          <el-col :span="24">
            <el-form-item label="时间戳：">{{
              snowflakeDecodeForm.timestamp
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="时间(易读格式)：">{{
              snowflakeDecodeForm.time
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="数据中心：">{{
              snowflakeDecodeForm.dataCenterId
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="机器ID：">{{
              snowflakeDecodeForm.workerId
            }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="随机序列号：">{{
              snowflakeDecodeForm.seq
            }}</el-form-item>
          </el-col>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="snowflakeOpen = false">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="BASE32编解码" :visible.sync="base32Flag">
      <el-form
        ref="base32Form"
        :model="base32Form"
        :inline="true"
        label-width="120px"
      >
        <el-form-item label="编码值" prop="encodeVal">
          <el-input
            v-model="base32Form.id"
            placeholder="请输入编码值"
            clearable
            style="width: 240px"
            size="small"
            @keyup.enter.native="handleEncodeFor32"
          />
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleEncodeFor32"
            >BASE32编码</el-button
          >
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleDecodeFor32"
            >BASE32解码</el-button
          >
        </el-form-item>

        <el-form-item label="结果：">{{ base32Form.result }}</el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="BASE62编解码" :visible.sync="base62Flag">
      <el-form
        ref="base62Form"
        :model="base62Form"
        :inline="true"
        label-width="120px"
      >
        <el-form-item label="编码值" prop="encodeVal">
          <el-input
            v-model="base62Form.id"
            placeholder="请输入编码值"
            clearable
            style="width: 240px"
            size="small"
            @keyup.enter.native="handleDecodeFor32"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleEncodeFor62"
            >BASE62编码</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleDecodeFor62"
            >BASE62解码</el-button
          >
        </el-form-item>
        <el-form-item label="结果：">{{ base62Form.result }}</el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {
  list,
  getBizKey,
  addSegment,
  updateSegment,
  changeSegmentStatus,
  decode,
  getSegmentKey,
  getSnowflake,
  base32encode,
  base32decode,
  base62encode,
  base62decode
} from "@/api/serial/index";

export default {
  name: "Serial",
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
        name: undefined
      },
      // snowflake流水号对话框
      snowflakeOpen: false,
      snowflakeQueryForm: {
        id: undefined
      },
      snowflakeDecodeOpen: false,
      snowflakeDecodeForm: {
        timestamp: 0,
        time: "",
        dataCenterId: 0,
        workerId: 0,
        seq: 0
      },
      rules: {
        id: [
          {
            required: true,
            message: "snowflake流水号不能为空",
            trigger: "blur"
          },
          {
            pattern: /^[0-9]*$/,
            message: "请输入正确的snowflake流水号",
            trigger: "blur"
          }
        ]
      },
      base32Flag: false,
      base32Form: {
        id: undefined,
        result: ""
      },
      base62Flag: false,
      base62Form: {
        id: undefined,
        result: ""
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询登录日志 */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
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
      this.keys = selection.map(item => item.key);
      this.multiple = !selection.length;
    },

    handleStatusChange(row) {
      const text = row.status == "0" ? "启用" : "停用";
      this.$confirm("确认要" + text + '"' + row.key + '"的业务key?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return changeSegmentStatus(row.key, row.status);
        })
        .then(() => {
          this.msgSuccess(text + "成功");
          this.getList();
        })
        .catch(function() {
          row.status = row.status == "0" ? "1" : "0";
        });
    },

    // 新增
    handleAdd() {
      this.addOrEdit = "add";
      this.resetForm("form");
      this.title = "添加序列号";
      this.open = true;
    },

    handleGet(row) {
      const key = row.key || this.keys;
      getSegmentKey(key).then(response => {
        this.msgSuccess(response.data);
      });
    },

    // 编辑
    handleUpdate(row) {
      this.resetForm("form");
      this.addOrEdit = "edit";
      const key = row.key || this.keys;
      getBizKey(key).then(response => {
        this.form = response.data;
        this.title = "修改序列号";
        this.open = true;
      });
    },

    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.addOrEdit == "edit") {
            updateSegment(this.form).then(response => {
              if (response.code == 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addSegment(this.form).then(response => {
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
        type: "warning"
      }).then(function() {
        return changeSegmentStatus(key, 2);
      }) .then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
    },

    handleSnowflakeDialog() {
      this.resetForm("snowflakeQueryForm");
      this.resetForm("snowflakeDecodeForm");
      // 获取snowflake流水号
      getSnowflake().then(response => {
        this.snowflakeQueryForm.id = response.data;
        // 解码流水号
        decode(this.snowflakeQueryForm).then(response => {
          this.snowflakeDecodeForm = response.data;
        });
        this.snowflakeOpen = true;
      });
    },

    // 解码snowflake流水号
    handleSnowflakeQuery() {
      this.$refs["snowflakeQueryForm"].validate(valid => {
        if (valid) {
          this.snowflakeDecodeOpen = false;
          decode(this.snowflakeQueryForm).then(response => {
            this.snowflakeDecodeOpen = true;
            this.snowflakeDecodeForm = response.data;
          });
        }
      });
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
