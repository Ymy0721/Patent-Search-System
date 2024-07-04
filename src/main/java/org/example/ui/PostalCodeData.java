package org.example.ui;

import java.util.HashMap;
import java.util.Map;

public class PostalCodeData {

    public static final Map<String, String> PROVINCE_POSTAL_CODE_MAP = new HashMap<>();
    public static final Map<String, String> CITY_POSTAL_CODE_MAP = new HashMap<>();


    // 这里我是在网上城市邮编库复制下来的,用于区域分析时根据邮编显示省市
    //邮编库网址：https://www.youbianku.cn/city
    static {
        PROVINCE_POSTAL_CODE_MAP.put("10", "北京市");
        PROVINCE_POSTAL_CODE_MAP.put("30", "天津市");
        PROVINCE_POSTAL_CODE_MAP.put("05", "河北省");
        PROVINCE_POSTAL_CODE_MAP.put("06", "河北省");
        PROVINCE_POSTAL_CODE_MAP.put("07", "河北省");
        PROVINCE_POSTAL_CODE_MAP.put("03", "山西省");
        PROVINCE_POSTAL_CODE_MAP.put("04", "山西省");
        PROVINCE_POSTAL_CODE_MAP.put("01", "内蒙古自治区");
        PROVINCE_POSTAL_CODE_MAP.put("00", "内蒙古自治区");
        PROVINCE_POSTAL_CODE_MAP.put("19", "内蒙古自治区");
        PROVINCE_POSTAL_CODE_MAP.put("02", "内蒙古自治区");
        PROVINCE_POSTAL_CODE_MAP.put("12", "辽宁省");
        PROVINCE_POSTAL_CODE_MAP.put("11", "辽宁省");
        PROVINCE_POSTAL_CODE_MAP.put("13", "吉林省");
        PROVINCE_POSTAL_CODE_MAP.put("15", "黑龙江省");
        PROVINCE_POSTAL_CODE_MAP.put("16", "黑龙江省");
        PROVINCE_POSTAL_CODE_MAP.put("20", "上海市");
        PROVINCE_POSTAL_CODE_MAP.put("21", "江苏省");
        PROVINCE_POSTAL_CODE_MAP.put("22", "江苏省");
        PROVINCE_POSTAL_CODE_MAP.put("31", "浙江省");
        PROVINCE_POSTAL_CODE_MAP.put("32", "浙江省");
        PROVINCE_POSTAL_CODE_MAP.put("24", "安徽省");
        PROVINCE_POSTAL_CODE_MAP.put("23", "安徽省");
        PROVINCE_POSTAL_CODE_MAP.put("35", "福建省");
        PROVINCE_POSTAL_CODE_MAP.put("36", "福建省");
        PROVINCE_POSTAL_CODE_MAP.put("33", "江西省");
        PROVINCE_POSTAL_CODE_MAP.put("34", "江西省");
        PROVINCE_POSTAL_CODE_MAP.put("26", "山东省");
        PROVINCE_POSTAL_CODE_MAP.put("25", "山东省");
        PROVINCE_POSTAL_CODE_MAP.put("27", "山东省");
        PROVINCE_POSTAL_CODE_MAP.put("45", "河南省");
        PROVINCE_POSTAL_CODE_MAP.put("46", "河南省");
        PROVINCE_POSTAL_CODE_MAP.put("47", "河南省");
        PROVINCE_POSTAL_CODE_MAP.put("43", "湖北省");
        PROVINCE_POSTAL_CODE_MAP.put("44", "湖北省");
        PROVINCE_POSTAL_CODE_MAP.put("42", "湖南省");
        PROVINCE_POSTAL_CODE_MAP.put("41", "湖南省");
        PROVINCE_POSTAL_CODE_MAP.put("52", "广东省");
        PROVINCE_POSTAL_CODE_MAP.put("51", "广东省");
        PROVINCE_POSTAL_CODE_MAP.put("53", "广西壮族自治区");
        PROVINCE_POSTAL_CODE_MAP.put("54", "广西壮族自治区");
        PROVINCE_POSTAL_CODE_MAP.put("57", "海南省");
        PROVINCE_POSTAL_CODE_MAP.put("40", "重庆市");
        PROVINCE_POSTAL_CODE_MAP.put("61", "四川省");
        PROVINCE_POSTAL_CODE_MAP.put("62", "四川省");
        PROVINCE_POSTAL_CODE_MAP.put("63", "四川省");
        PROVINCE_POSTAL_CODE_MAP.put("64", "四川省");
        PROVINCE_POSTAL_CODE_MAP.put("56", "贵州省");
        PROVINCE_POSTAL_CODE_MAP.put("55", "贵州省");
        PROVINCE_POSTAL_CODE_MAP.put("67", "云南省");
        PROVINCE_POSTAL_CODE_MAP.put("66", "云南省");
        PROVINCE_POSTAL_CODE_MAP.put("65", "云南省");
        PROVINCE_POSTAL_CODE_MAP.put("85", "西藏自治区");
        PROVINCE_POSTAL_CODE_MAP.put("86", "西藏自治区");
        PROVINCE_POSTAL_CODE_MAP.put("89", "西藏自治区");
        PROVINCE_POSTAL_CODE_MAP.put("72", "陕西省");
        PROVINCE_POSTAL_CODE_MAP.put("71", "陕西省");
        PROVINCE_POSTAL_CODE_MAP.put("73", "甘肃省");
        PROVINCE_POSTAL_CODE_MAP.put("74", "甘肃省");
        PROVINCE_POSTAL_CODE_MAP.put("81", "青海省");
        PROVINCE_POSTAL_CODE_MAP.put("75", "宁夏回族自治区");
        PROVINCE_POSTAL_CODE_MAP.put("83", "新疆维吾尔自治区");
        PROVINCE_POSTAL_CODE_MAP.put("84", "新疆维吾尔自治区");
    }
    static {
        // 天津
        CITY_POSTAL_CODE_MAP.put("3000", "天津市");

        // 北京
        CITY_POSTAL_CODE_MAP.put("1000", "北京市");

        // 新疆维吾尔自治区
        CITY_POSTAL_CODE_MAP.put("8300", "乌鲁木齐市");
        CITY_POSTAL_CODE_MAP.put("8351", "伊犁哈萨克自治州");
        CITY_POSTAL_CODE_MAP.put("8453", "克孜勒苏柯尔克孜自治州");
        CITY_POSTAL_CODE_MAP.put("8340", "克拉玛依市");
        CITY_POSTAL_CODE_MAP.put("8334", "博尔塔拉蒙古自治州");
        CITY_POSTAL_CODE_MAP.put("8388", "吐鲁番地区");
        CITY_POSTAL_CODE_MAP.put("8480", "和田地区");
        CITY_POSTAL_CODE_MAP.put("8390", "哈密地区");
        CITY_POSTAL_CODE_MAP.put("8444", "喀什地区");
        CITY_POSTAL_CODE_MAP.put("8347", "塔城地区");
        CITY_POSTAL_CODE_MAP.put("8410", "巴音郭楞蒙古自治州");
        CITY_POSTAL_CODE_MAP.put("8311", "昌吉回族自治州");
        CITY_POSTAL_CODE_MAP.put("8320", "石河子市");
        CITY_POSTAL_CODE_MAP.put("8430", "阿克苏地区");
        CITY_POSTAL_CODE_MAP.put("8365", "阿勒泰地区");
        CITY_POSTAL_CODE_MAP.put("8433", "阿拉尔市");

        // 宁夏回族自治区
        CITY_POSTAL_CODE_MAP.put("7550", "中卫市");
        CITY_POSTAL_CODE_MAP.put("7511", "吴忠市");
        CITY_POSTAL_CODE_MAP.put("7560", "固原市");
        CITY_POSTAL_CODE_MAP.put("7530", "石嘴山市");
        CITY_POSTAL_CODE_MAP.put("7500", "银川市");

        // 青海省
        CITY_POSTAL_CODE_MAP.put("8140", "果洛藏族自治州");
        CITY_POSTAL_CODE_MAP.put("8106", "海东地区");
        CITY_POSTAL_CODE_MAP.put("8122", "海北藏族自治州");
        CITY_POSTAL_CODE_MAP.put("8130", "海南藏族自治州");
        CITY_POSTAL_CODE_MAP.put("8170", "海西蒙古族藏族自治州");
        CITY_POSTAL_CODE_MAP.put("8150", "玉树藏族自治州");
        CITY_POSTAL_CODE_MAP.put("8100", "西宁市");
        CITY_POSTAL_CODE_MAP.put("8113", "黄南藏族自治州");

        // 甘肃省
        CITY_POSTAL_CODE_MAP.put("7311", "临夏回族自治州");
        CITY_POSTAL_CODE_MAP.put("7300", "兰州市");
        CITY_POSTAL_CODE_MAP.put("7351", "嘉峪关市");
        CITY_POSTAL_CODE_MAP.put("7410", "天水市");
        CITY_POSTAL_CODE_MAP.put("7432", "定西市");
        CITY_POSTAL_CODE_MAP.put("7444", "平凉市");
        CITY_POSTAL_CODE_MAP.put("7450", "庆阳市");
        CITY_POSTAL_CODE_MAP.put("7340", "张掖市");
        CITY_POSTAL_CODE_MAP.put("7330", "武威市");
        CITY_POSTAL_CODE_MAP.put("7470", "甘南藏族自治州");
        CITY_POSTAL_CODE_MAP.put("7309", "白银市");
        CITY_POSTAL_CODE_MAP.put("7350", "酒泉市");
        CITY_POSTAL_CODE_MAP.put("7371", "金昌市");
        CITY_POSTAL_CODE_MAP.put("7425", "陇南市");

        // 陕西省
        CITY_POSTAL_CODE_MAP.put("7120", "咸阳市");
        CITY_POSTAL_CODE_MAP.put("7260", "商洛市");
        CITY_POSTAL_CODE_MAP.put("7250", "安康市");
        CITY_POSTAL_CODE_MAP.put("7210", "宝鸡市");
        CITY_POSTAL_CODE_MAP.put("7160", "延安市");
        CITY_POSTAL_CODE_MAP.put("7190", "榆林市");
        CITY_POSTAL_CODE_MAP.put("7230", "汉中市");
        CITY_POSTAL_CODE_MAP.put("7140", "渭南市");
        CITY_POSTAL_CODE_MAP.put("7100", "西安市");
        CITY_POSTAL_CODE_MAP.put("7270", "铜川市");

        // 西藏自治区
        CITY_POSTAL_CODE_MAP.put("8560", "山南地区");
        CITY_POSTAL_CODE_MAP.put("8500", "拉萨市");
        CITY_POSTAL_CODE_MAP.put("8570", "日喀则地区");
        CITY_POSTAL_CODE_MAP.put("8540", "昌都地区");
        CITY_POSTAL_CODE_MAP.put("8600", "林芝地区");
        CITY_POSTAL_CODE_MAP.put("8520", "那曲地区");
        CITY_POSTAL_CODE_MAP.put("8590", "阿里地区");

        // 重庆
        CITY_POSTAL_CODE_MAP.put("4000", "重庆市");

        // 海南省
        CITY_POSTAL_CODE_MAP.put("5715", "万宁市");
        CITY_POSTAL_CODE_MAP.put("5720", "三亚市");
        CITY_POSTAL_CODE_MAP.put("5726", "东方市");
        CITY_POSTAL_CODE_MAP.put("5718", "临高县");
        CITY_POSTAL_CODE_MAP.put("5725", "乐东黎族自治县");
        CITY_POSTAL_CODE_MAP.put("5722", "五指山市");
        CITY_POSTAL_CODE_MAP.put("5723", "保亭黎族苗族自治县");
        CITY_POSTAL_CODE_MAP.put("5717", "儋州市");
        CITY_POSTAL_CODE_MAP.put("5712", "定安县");
        CITY_POSTAL_CODE_MAP.put("5716", "屯昌县");
        CITY_POSTAL_CODE_MAP.put("5713", "文昌市");
        CITY_POSTAL_CODE_MAP.put("5727", "昌江黎族自治县");
        CITY_POSTAL_CODE_MAP.put("5701", "海口市");
        CITY_POSTAL_CODE_MAP.put("5719", "澄迈县");
        CITY_POSTAL_CODE_MAP.put("5729", "琼中黎族苗族自治县");
        CITY_POSTAL_CODE_MAP.put("5714", "琼海市");
        CITY_POSTAL_CODE_MAP.put("5728", "白沙黎族自治县");
        CITY_POSTAL_CODE_MAP.put("5724", "陵水黎族自治县");

        // 广西壮族自治区
        CITY_POSTAL_CODE_MAP.put("5360", "北海市");
        CITY_POSTAL_CODE_MAP.put("5300", "南宁市");
        CITY_POSTAL_CODE_MAP.put("5322", "崇左市");
        CITY_POSTAL_CODE_MAP.put("5461", "来宾市");
        CITY_POSTAL_CODE_MAP.put("5450", "柳州市");
        CITY_POSTAL_CODE_MAP.put("5410", "桂林市");
        CITY_POSTAL_CODE_MAP.put("5430", "梧州市");
        CITY_POSTAL_CODE_MAP.put("5470", "河池市");
        CITY_POSTAL_CODE_MAP.put("5370", "玉林市");
        CITY_POSTAL_CODE_MAP.put("5428", "贺州市");
        CITY_POSTAL_CODE_MAP.put("5350", "钦州市");
        CITY_POSTAL_CODE_MAP.put("5380", "防城港市");

        // 湖南省
        CITY_POSTAL_CODE_MAP.put("4170", "娄底市");
        CITY_POSTAL_CODE_MAP.put("4140", "岳阳市");
        CITY_POSTAL_CODE_MAP.put("4150", "常德市");
        CITY_POSTAL_CODE_MAP.put("4270", "张家界市");
        CITY_POSTAL_CODE_MAP.put("4180", "怀化市");
        CITY_POSTAL_CODE_MAP.put("4120", "株洲市");
        CITY_POSTAL_CODE_MAP.put("4250", "永州市");
        CITY_POSTAL_CODE_MAP.put("4111", "湘潭市");
        CITY_POSTAL_CODE_MAP.put("4160", "湘西土家族苗族自治州");
        CITY_POSTAL_CODE_MAP.put("4130", "益阳市");
        CITY_POSTAL_CODE_MAP.put("4212", "衡阳市");
        CITY_POSTAL_CODE_MAP.put("4220", "邵阳市");
        CITY_POSTAL_CODE_MAP.put("4230", "郴州市");
        CITY_POSTAL_CODE_MAP.put("4100", "长沙市");

        // 云南省
        CITY_POSTAL_CODE_MAP.put("6770", "临沧市");
        CITY_POSTAL_CODE_MAP.put("6741", "丽江市");
        CITY_POSTAL_CODE_MAP.put("6780", "保山市");
        CITY_POSTAL_CODE_MAP.put("6710", "大理白族自治州");
        CITY_POSTAL_CODE_MAP.put("6784", "德宏傣族景颇族自治州");
        CITY_POSTAL_CODE_MAP.put("6731", "怒江傈僳族自治州");
        CITY_POSTAL_CODE_MAP.put("6630", "文山壮族苗族自治州");
        CITY_POSTAL_CODE_MAP.put("6500", "昆明市");
        CITY_POSTAL_CODE_MAP.put("6570", "昭通市");
        CITY_POSTAL_CODE_MAP.put("6650", "普洱市");
        CITY_POSTAL_CODE_MAP.put("6550", "曲靖市");
        CITY_POSTAL_CODE_MAP.put("6750", "楚雄彝族自治州");
        CITY_POSTAL_CODE_MAP.put("6531", "玉溪市");
        CITY_POSTAL_CODE_MAP.put("6514", "红河哈尼族彝族自治州");
        CITY_POSTAL_CODE_MAP.put("6661", "西双版纳傣族自治州");
        CITY_POSTAL_CODE_MAP.put("6744", "迪庆藏族自治州");

        // 贵州省
        CITY_POSTAL_CODE_MAP.put("5530", "六盘水市");
        CITY_POSTAL_CODE_MAP.put("5610", "安顺市");
        CITY_POSTAL_CODE_MAP.put("5517", "毕节地区");
        CITY_POSTAL_CODE_MAP.put("5500", "贵阳市");
        CITY_POSTAL_CODE_MAP.put("5630", "遵义市");
        CITY_POSTAL_CODE_MAP.put("5543", "铜仁地区");
        CITY_POSTAL_CODE_MAP.put("5560", "黔东南苗族侗族自治州");
        CITY_POSTAL_CODE_MAP.put("5580", "黔南布依族苗族自治州");
        CITY_POSTAL_CODE_MAP.put("5624", "黔西南布依族苗族自治州");

        // 四川省
        CITY_POSTAL_CODE_MAP.put("6140", "乐山市");
        CITY_POSTAL_CODE_MAP.put("6410", "内江市");
        CITY_POSTAL_CODE_MAP.put("6150", "凉山彝族自治州");
        CITY_POSTAL_CODE_MAP.put("6370", "南充市");
        CITY_POSTAL_CODE_MAP.put("6440", "宜宾市");
        CITY_POSTAL_CODE_MAP.put("6366", "巴中市");
        CITY_POSTAL_CODE_MAP.put("6280", "广元市");
        CITY_POSTAL_CODE_MAP.put("6385", "广安市");
        CITY_POSTAL_CODE_MAP.put("6180", "德阳市");
        CITY_POSTAL_CODE_MAP.put("6100", "成都市");
        CITY_POSTAL_CODE_MAP.put("6170", "攀枝花市");
        CITY_POSTAL_CODE_MAP.put("6460", "泸州市");
        CITY_POSTAL_CODE_MAP.put("6267", "甘孜藏族自治州");
        CITY_POSTAL_CODE_MAP.put("6200", "眉山市");
        CITY_POSTAL_CODE_MAP.put("6210", "绵阳市");
        CITY_POSTAL_CODE_MAP.put("6430", "自贡市");
        CITY_POSTAL_CODE_MAP.put("6413", "资阳市");
        CITY_POSTAL_CODE_MAP.put("6350", "达州市");
        CITY_POSTAL_CODE_MAP.put("6290", "遂宁市");
        CITY_POSTAL_CODE_MAP.put("6240", "阿坝藏族羌族自治州");
        CITY_POSTAL_CODE_MAP.put("6250", "雅安市");

        // 广东省
        CITY_POSTAL_CODE_MAP.put("5230", "东莞市");
        CITY_POSTAL_CODE_MAP.put("5284", "中山市");
        CITY_POSTAL_CODE_MAP.put("5273", "云浮市");
        CITY_POSTAL_CODE_MAP.put("5280", "佛山市");
        CITY_POSTAL_CODE_MAP.put("5100", "广州市");
        CITY_POSTAL_CODE_MAP.put("5160", "惠州市");
        CITY_POSTAL_CODE_MAP.put("5220", "揭阳市");
        CITY_POSTAL_CODE_MAP.put("5140", "梅州市");
        CITY_POSTAL_CODE_MAP.put("5150", "汕头市");
        CITY_POSTAL_CODE_MAP.put("5166", "汕尾市");
        CITY_POSTAL_CODE_MAP.put("5290", "江门市");
        CITY_POSTAL_CODE_MAP.put("5170", "河源市");
        CITY_POSTAL_CODE_MAP.put("5180", "深圳市");
        CITY_POSTAL_CODE_MAP.put("5115", "清远市");
        CITY_POSTAL_CODE_MAP.put("5240", "湛江市");
        CITY_POSTAL_CODE_MAP.put("5210", "潮州市");
        CITY_POSTAL_CODE_MAP.put("5190", "珠海市");
        CITY_POSTAL_CODE_MAP.put("5260", "肇庆市");
        CITY_POSTAL_CODE_MAP.put("5250", "茂名市");
        CITY_POSTAL_CODE_MAP.put("5295", "阳江市");
        CITY_POSTAL_CODE_MAP.put("5120", "韶关市");

        // 河南省
        CITY_POSTAL_CODE_MAP.put("4720", "三门峡市");
        CITY_POSTAL_CODE_MAP.put("4640", "信阳市");
        CITY_POSTAL_CODE_MAP.put("4730", "南阳市");
        CITY_POSTAL_CODE_MAP.put("4660", "周口市");
        CITY_POSTAL_CODE_MAP.put("4760", "商丘市");
        CITY_POSTAL_CODE_MAP.put("4550", "安阳市");
        CITY_POSTAL_CODE_MAP.put("4670", "平顶山市");
        CITY_POSTAL_CODE_MAP.put("4750", "开封市");
        CITY_POSTAL_CODE_MAP.put("4710", "洛阳市");
        CITY_POSTAL_CODE_MAP.put("4546", "济源市");
        CITY_POSTAL_CODE_MAP.put("4620", "漯河市");
        CITY_POSTAL_CODE_MAP.put("4570", "濮阳市");
        CITY_POSTAL_CODE_MAP.put("4541", "焦作市");
        CITY_POSTAL_CODE_MAP.put("4610", "许昌市");
        CITY_POSTAL_CODE_MAP.put("4500", "郑州市");
        CITY_POSTAL_CODE_MAP.put("4630", "驻马店市");
        CITY_POSTAL_CODE_MAP.put("4580", "鹤壁市");

        // 湖北省
        CITY_POSTAL_CODE_MAP.put("4330", "仙桃市");
        CITY_POSTAL_CODE_MAP.put("4420", "十堰市");
        CITY_POSTAL_CODE_MAP.put("4370", "咸宁市");
        CITY_POSTAL_CODE_MAP.put("4317", "天门市");
        CITY_POSTAL_CODE_MAP.put("4320", "孝感市");
        CITY_POSTAL_CODE_MAP.put("4430", "宜昌市");
        CITY_POSTAL_CODE_MAP.put("4450", "恩施土家族苗族自治州");
        CITY_POSTAL_CODE_MAP.put("4300", "武汉市");
        CITY_POSTAL_CODE_MAP.put("4331", "潜江市");
        CITY_POSTAL_CODE_MAP.put("4424", "神农架林区");
        CITY_POSTAL_CODE_MAP.put("4340", "荆州市");
        CITY_POSTAL_CODE_MAP.put("4480", "荆门市");
        CITY_POSTAL_CODE_MAP.put("4410", "襄樊市");
        CITY_POSTAL_CODE_MAP.put("4360", "鄂州市");
        CITY_POSTAL_CODE_MAP.put("4413", "随州市");
        CITY_POSTAL_CODE_MAP.put("4380", "黄冈市");
        CITY_POSTAL_CODE_MAP.put("4350", "黄石市");

        // 江西省
        CITY_POSTAL_CODE_MAP.put("3340", "上饶市");
        CITY_POSTAL_CODE_MAP.put("3320", "九江市");
        CITY_POSTAL_CODE_MAP.put("3300", "南昌市");
        CITY_POSTAL_CODE_MAP.put("3430", "吉安市");
        CITY_POSTAL_CODE_MAP.put("3360", "宜春市");
        CITY_POSTAL_CODE_MAP.put("3440", "抚州市");
        CITY_POSTAL_CODE_MAP.put("3380", "新余市");
        CITY_POSTAL_CODE_MAP.put("3330", "景德镇市");
        CITY_POSTAL_CODE_MAP.put("3370", "萍乡市");
        CITY_POSTAL_CODE_MAP.put("3410", "赣州市");
        CITY_POSTAL_CODE_MAP.put("3350", "鹰潭市");

        // 山东省
        CITY_POSTAL_CODE_MAP.put("2570", "东营市");
        CITY_POSTAL_CODE_MAP.put("2760", "临沂市");
        CITY_POSTAL_CODE_MAP.put("2642", "威海市");
        CITY_POSTAL_CODE_MAP.put("2530", "德州市");
        CITY_POSTAL_CODE_MAP.put("2768", "日照市");
        CITY_POSTAL_CODE_MAP.put("2770", "枣庄市");
        CITY_POSTAL_CODE_MAP.put("2710", "泰安市");
        CITY_POSTAL_CODE_MAP.put("2500", "济南市");
        CITY_POSTAL_CODE_MAP.put("2720", "济宁市");
        CITY_POSTAL_CODE_MAP.put("2550", "淄博市");
        CITY_POSTAL_CODE_MAP.put("2566", "滨州市");
        CITY_POSTAL_CODE_MAP.put("2610", "潍坊市");
        CITY_POSTAL_CODE_MAP.put("2640", "烟台市");
        CITY_POSTAL_CODE_MAP.put("2520", "聊城市");
        CITY_POSTAL_CODE_MAP.put("2711", "莱芜市");
        CITY_POSTAL_CODE_MAP.put("2740", "菏泽市");
        CITY_POSTAL_CODE_MAP.put("2660", "青岛市");

        // 福建省
        CITY_POSTAL_CODE_MAP.put("3530", "三明市");
        CITY_POSTAL_CODE_MAP.put("3610", "厦门市");
        CITY_POSTAL_CODE_MAP.put("3520", "宁德市");
        CITY_POSTAL_CODE_MAP.put("3620", "泉州市");
        CITY_POSTAL_CODE_MAP.put("3630", "漳州市");
        CITY_POSTAL_CODE_MAP.put("3500", "福州市");
        CITY_POSTAL_CODE_MAP.put("3511", "莆田市");

        // 浙江省
        CITY_POSTAL_CODE_MAP.put("3230", "丽水市");
        CITY_POSTAL_CODE_MAP.put("3180", "台州市");
        CITY_POSTAL_CODE_MAP.put("3140", "嘉兴市");
        CITY_POSTAL_CODE_MAP.put("3150", "宁波市");
        CITY_POSTAL_CODE_MAP.put("3100", "杭州市");
        CITY_POSTAL_CODE_MAP.put("3250", "温州市");
        CITY_POSTAL_CODE_MAP.put("3130", "湖州市");
        CITY_POSTAL_CODE_MAP.put("3120", "绍兴市");
        CITY_POSTAL_CODE_MAP.put("3160", "舟山市");
        CITY_POSTAL_CODE_MAP.put("3240", "衢州市");
        CITY_POSTAL_CODE_MAP.put("3210", "金华市");

        // 安徽省
        CITY_POSTAL_CODE_MAP.put("2360", "亳州市");
        CITY_POSTAL_CODE_MAP.put("2370", "六安市");
        CITY_POSTAL_CODE_MAP.put("2300", "合肥市");
        CITY_POSTAL_CODE_MAP.put("2460", "安庆市");
        CITY_POSTAL_CODE_MAP.put("2420", "宣城市");
        CITY_POSTAL_CODE_MAP.put("2340", "宿州市");
        CITY_POSTAL_CODE_MAP.put("2380", "巢湖市");
        CITY_POSTAL_CODE_MAP.put("2471", "池州市");
        CITY_POSTAL_CODE_MAP.put("2350", "淮北市");
        CITY_POSTAL_CODE_MAP.put("2320", "淮南市");
        CITY_POSTAL_CODE_MAP.put("2390", "滁州市");
        CITY_POSTAL_CODE_MAP.put("2410", "芜湖市");
        CITY_POSTAL_CODE_MAP.put("2330", "蚌埠市");
        CITY_POSTAL_CODE_MAP.put("2440", "铜陵市");
        CITY_POSTAL_CODE_MAP.put("2430", "马鞍山市");
        CITY_POSTAL_CODE_MAP.put("2450", "黄山市");

        // 上海市
        CITY_POSTAL_CODE_MAP.put("2000", "上海市");

        // 江苏省
        CITY_POSTAL_CODE_MAP.put("2100", "南京市");
        CITY_POSTAL_CODE_MAP.put("2260", "南通市");
        CITY_POSTAL_CODE_MAP.put("2238", "宿迁市");
        CITY_POSTAL_CODE_MAP.put("2130", "常州市");
        CITY_POSTAL_CODE_MAP.put("2210", "徐州市");
        CITY_POSTAL_CODE_MAP.put("2250", "扬州市");
        CITY_POSTAL_CODE_MAP.put("2140", "无锡市");
        CITY_POSTAL_CODE_MAP.put("2153", "泰州市");
        CITY_POSTAL_CODE_MAP.put("2230", "淮安市");
        CITY_POSTAL_CODE_MAP.put("2240", "盐城市");
        CITY_POSTAL_CODE_MAP.put("2150", "苏州市");
        CITY_POSTAL_CODE_MAP.put("2220", "连云港市");
        CITY_POSTAL_CODE_MAP.put("2120", "镇江市");

        // 河北省
        CITY_POSTAL_CODE_MAP.put("0710", "保定市");
        CITY_POSTAL_CODE_MAP.put("0630", "唐山市");
        CITY_POSTAL_CODE_MAP.put("0650", "廊坊市");
        CITY_POSTAL_CODE_MAP.put("0750", "张家口市");
        CITY_POSTAL_CODE_MAP.put("0670", "承德市");
        CITY_POSTAL_CODE_MAP.put("0610", "沧州市");
        CITY_POSTAL_CODE_MAP.put("0500", "石家庄市");
        CITY_POSTAL_CODE_MAP.put("0660", "秦皇岛市");
        CITY_POSTAL_CODE_MAP.put("0530", "衡水市");
        CITY_POSTAL_CODE_MAP.put("0540", "邢台市");
        CITY_POSTAL_CODE_MAP.put("0560", "邯郸市");

        // 吉林省
        CITY_POSTAL_CODE_MAP.put("1320", "吉林市");
        CITY_POSTAL_CODE_MAP.put("1360", "四平市");
        CITY_POSTAL_CODE_MAP.put("1330", "延边朝鲜族自治州");
        CITY_POSTAL_CODE_MAP.put("1380", "松原市");
        CITY_POSTAL_CODE_MAP.put("1370", "白城市");
        CITY_POSTAL_CODE_MAP.put("1343", "白山市");
        CITY_POSTAL_CODE_MAP.put("1362", "辽源市");
        CITY_POSTAL_CODE_MAP.put("1340", "通化市");
        CITY_POSTAL_CODE_MAP.put("1300", "长春市");

        // 辽宁省
        CITY_POSTAL_CODE_MAP.put("1180", "丹东市");
        CITY_POSTAL_CODE_MAP.put("1160", "大连市");
        CITY_POSTAL_CODE_MAP.put("1130", "抚顺市");
        CITY_POSTAL_CODE_MAP.put("1220", "朝阳市");
        CITY_POSTAL_CODE_MAP.put("1170", "本溪市");
        CITY_POSTAL_CODE_MAP.put("1100", "沈阳市");
        CITY_POSTAL_CODE_MAP.put("1240", "盘锦市");
        CITY_POSTAL_CODE_MAP.put("1150", "营口市");
        CITY_POSTAL_CODE_MAP.put("1250", "葫芦岛市");
        CITY_POSTAL_CODE_MAP.put("1110", "辽阳市");
        CITY_POSTAL_CODE_MAP.put("1120", "铁岭市");
        CITY_POSTAL_CODE_MAP.put("1210", "锦州市");
        CITY_POSTAL_CODE_MAP.put("1230", "阜新市");
        CITY_POSTAL_CODE_MAP.put("1140", "鞍山市");

        // 黑龙江省
        CITY_POSTAL_CODE_MAP.put("1546", "七台河市");
        CITY_POSTAL_CODE_MAP.put("1530", "伊春市");
        CITY_POSTAL_CODE_MAP.put("1540", "佳木斯市");
        CITY_POSTAL_CODE_MAP.put("1551", "双鸭山市");
        CITY_POSTAL_CODE_MAP.put("1500", "哈尔滨市");
        CITY_POSTAL_CODE_MAP.put("1650", "大兴安岭地区");
        CITY_POSTAL_CODE_MAP.put("1630", "大庆市");
        CITY_POSTAL_CODE_MAP.put("1570", "牡丹江市");
        CITY_POSTAL_CODE_MAP.put("1520", "绥化市");
        CITY_POSTAL_CODE_MAP.put("1581", "鸡西市");
        CITY_POSTAL_CODE_MAP.put("1541", "鹤岗市");
        CITY_POSTAL_CODE_MAP.put("1643", "黑河市");
        CITY_POSTAL_CODE_MAP.put("1610", "齐齐哈尔市");

        // 内蒙古自治区
        CITY_POSTAL_CODE_MAP.put("0120", "乌兰察布市");
        CITY_POSTAL_CODE_MAP.put("0160", "乌海市");
        CITY_POSTAL_CODE_MAP.put("1374", "兴安盟");
        CITY_POSTAL_CODE_MAP.put("0140", "包头市");
        CITY_POSTAL_CODE_MAP.put("0210", "呼伦贝尔市");
        CITY_POSTAL_CODE_MAP.put("0100", "呼和浩特市");
        CITY_POSTAL_CODE_MAP.put("0150", "巴彦淖尔市");
        CITY_POSTAL_CODE_MAP.put("0240", "赤峰市");
        CITY_POSTAL_CODE_MAP.put("0280", "通辽市");
        CITY_POSTAL_CODE_MAP.put("0170", "鄂尔多斯市");
        CITY_POSTAL_CODE_MAP.put("0260", "锡林郭勒盟");
        CITY_POSTAL_CODE_MAP.put("7503", "阿拉善盟");

        // 山西省
        CITY_POSTAL_CODE_MAP.put("0410", "临汾市");
        CITY_POSTAL_CODE_MAP.put("0330", "吕梁市");
        CITY_POSTAL_CODE_MAP.put("0370", "大同市");
        CITY_POSTAL_CODE_MAP.put("0300", "太原市");
        CITY_POSTAL_CODE_MAP.put("0340", "忻州市");
        CITY_POSTAL_CODE_MAP.put("0306", "晋中市");
        CITY_POSTAL_CODE_MAP.put("0480", "晋城市");
        CITY_POSTAL_CODE_MAP.put("0360", "朔州市");
        CITY_POSTAL_CODE_MAP.put("0440", "运城市");
        CITY_POSTAL_CODE_MAP.put("0460", "长治市");
        CITY_POSTAL_CODE_MAP.put("0450", "阳泉市");
    }
}
