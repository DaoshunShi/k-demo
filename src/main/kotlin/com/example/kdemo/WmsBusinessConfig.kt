package com.example.kdemo

import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

class WmsBusinessConfig(
    val robot: RobotConfig = RobotConfig(),
    val stockIn: StockInConfig = StockInConfig(),
    val stockOut: StockOutConfig = StockOutConfig(),
    val assignBatchMode: AssignBatchMode = AssignBatchMode.DistrictAndRow,
    val transferSite: TransferSiteConfig = TransferSiteConfig(),
    val count: CountConfig = CountConfig(),
    val partStockFeatureConfigs: PartStockFeatureConfigs = PartStockFeatureConfigs(),
    val finance: WmsFinance = WmsFinance()
)

class RobotConfig(
    val robotModel: List<RobotModelConfig> = emptyList(), // 型号配置
    val siteNum: Int = 1, // 身上库位数，全局
)

class RobotModelConfig(
    val model: RobotModel = RobotModel.None,
    val siteNum: Int = 1, // 身上库位数，模型
    val categories: List<RobotCategory> = emptyList()
)

class RobotCategory(
    val name: String = "",
    val siteNum: Int = 1, // 身上库位数，类别
)

enum class RobotModel {
    None,
    Box,
    Fork,
    MiniFork,
    Jack,
    Shuttle,
    OtherAgv
}

class StockInConfig(
    // 自动产生批次号
    val autoLotNo: Boolean = false,
    // 入库，不要自动出容器
    val noAutoOutContainer: Boolean = false,
    // 入库，指定存储区域
    val storeDistrictOn: Boolean = false,
    // 入库，指定供应商
    val vendorOn: Boolean = false,
    // 类型列表
    val categories: List<String> = emptyList(),
    // 单行显示的物料字段
    val partFields: List<NameLabelWidth> = emptyList(),
    // 审批配置
    val preApproval: PreApproval = PreApproval(),
    // 打印配置 - 页眉
    val printCfgMap: Map<PrintType, PrintCfg> = emptyMap()
)

class StockOutConfig(
    // 出库，不要自动出容器
    val noAutoOutContainer: Boolean = false,
    // 整托出库库区
    val wholeTargetDistrict: String = "",
    // 非整托出库库区
    val notWholeTargetDistrict: String = "",
    // 检查库存是否足够
    val shortCheck: Boolean = false,
    // 类型列表
    val categories: List<String> = emptyList(),
    // 单行显示的物料字段
    val partFields: List<NameLabelWidth> = emptyList(),
    // 审批配置
    val preApproval: PreApproval = PreApproval(),
    // 打印配置 - 页眉
    val printCfgMap: Map<PrintType, PrintCfg> = emptyMap()
)

// 任务分组模式
enum class AssignBatchMode {
    DistrictAndRow,
    District,
    Row
}

class CountConfig(
    val workingDistrict: String = ""
)

class TransferSiteConfig(
    // 类型列表
    val categories: List<String> = emptyList()
)

class PartStockFeatureConfigs(
    val lotNo: PartStockFeatureConfig = PartStockFeatureConfig(),
    val serialNo: PartStockFeatureConfig = PartStockFeatureConfig(),
    val vendor: PartStockFeatureConfig = PartStockFeatureConfig(),
    val subModel: PartStockFeatureConfig = PartStockFeatureConfig(),
    val procedureCode: PartStockFeatureConfig = PartStockFeatureConfig(),
    val workOrder: PartStockFeatureConfig = PartStockFeatureConfig(),
    val customer: PartStockFeatureConfig = PartStockFeatureConfig(),
    val customerOrder: PartStockFeatureConfig = PartStockFeatureConfig(),
    val partExtra1: PartStockFeatureConfig = PartStockFeatureConfig(),
    val partExtra2: PartStockFeatureConfig = PartStockFeatureConfig(),
    val partExtra3: PartStockFeatureConfig = PartStockFeatureConfig(),
) {
    
    fun forEach(
        action: (
            p: KProperty1<PartStockFeatureConfigs, *>, config: PartStockFeatureConfig
        ) -> Unit
    ) {
        for (p in PartStockFeatureConfigs::class.declaredMemberProperties) {
            action.invoke(p, p.get(this) as PartStockFeatureConfig)
        }
    }
    
}

class PartStockFeatureConfig(
    var storeOn: Boolean = false, // 是否作为库存区分字段
    var stockInOn: Boolean = false, // 入库是是否启用该字段，注意，如果 storeOn 为 true，该字段应该也为 true
    var stockOutOn: Boolean = false, // 出库是否启用该字段
    var required: Boolean = false, // 是否必填
    var label: String = "", // 覆盖标签
    var unique: Boolean = false, // 此属性值是否导致相关记录全局唯一
    var options: List<OptionItem>? = null,
)

class OptionItem(
    val value: String = "",
    val label: String = ""
)

class NameLabelWidth(
    val name: String = "",
    val label: String = "",
    val width: Int = 0
)

class PreApproval(
    val enabled: Boolean = false,
    val steps: List<PreApprovalStep> = emptyList()
)

class PreApprovalStep(
    val label: String = "", // 环节名称
    val roleIds: List<String> = emptyList(), // 能够审批此环节的用户角色
    val abort: Boolean = false // 是否可以直接作废、终止
)

class PrintCfg(
    val msg: String = "",
    val horizontalAlign: HorizontalAlign = HorizontalAlign.CENTER,
    val fontSize: Float = 7.0f
)

enum class PrintType {
    Head, Foot, Page
}

enum class HorizontalAlign {
    CENTER, LEFT, RIGHT
}

enum class VerticalAlign {
    CENTER, TOP, BOTTOM
}

class WmsFinance(
    val prices: List<WmsPriceConfig> = emptyList(), // 目前支持三个价格
)

class WmsPriceConfig(
    val label: String = "", // 显示名
    //
    val priceStockInLineOn: Boolean = false, // 入库时是否显示价格，单行
    val priceStockInLineEditable: Boolean = false,
    val amountStockInLineOn: Boolean = false, // 入库时是否显示金额，单行
    val amountStockInTotalOn: Boolean = false, // 入库时是否显示金额，单头
    //
    val priceStockOutLineOn: Boolean = false, // 出库时是否显示价格，单行
    val priceStockOutLineEditable: Boolean = false,
    val amountStockOutLineOn: Boolean = false, // 出库时是否显示金额，单行
    val amountStockOutTotalOn: Boolean = false, // 出库时是否显示金额，单头
)