/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.5.62 : Database - shopping
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shopping` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `shopping`;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `cid` varchar(32) NOT NULL,
  `cname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`cid`,`cname`) values ('1','手机数码'),('2','电脑办公'),('3','家具家居'),('4','鞋靴箱包'),('5','图书音像'),('6','母婴孕婴'),('B5BF5A59AA084C63BD67983BEDFA4606','成人用品');

/*Table structure for table `orderitem` */

DROP TABLE IF EXISTS `orderitem`;

CREATE TABLE `orderitem` (
  `count` int(11) DEFAULT NULL,
  `subtotal` double DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `oid` varchar(32) DEFAULT NULL,
  KEY `fk_0001` (`pid`),
  KEY `fk_0002` (`oid`),
  CONSTRAINT `fk_0001` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
  CONSTRAINT `fk_0002` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orderitem` */

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `oid` varchar(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` double DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `uid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `pid` varchar(32) NOT NULL,
  `pname` varchar(50) DEFAULT NULL,
  `market_price` double DEFAULT NULL,
  `shop_price` double DEFAULT NULL,
  `pimage` varchar(200) DEFAULT NULL,
  `pdate` date DEFAULT NULL,
  `is_hot` int(11) DEFAULT NULL,
  `pdesc` varchar(255) DEFAULT NULL,
  `pflag` int(11) DEFAULT NULL,
  `cid` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `sfk_0001` (`cid`),
  CONSTRAINT `sfk_0001` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`pid`,`pname`,`market_price`,`shop_price`,`pimage`,`pdate`,`is_hot`,`pdesc`,`pflag`,`cid`) values ('1','小米 4c 标准版',1399,1299,'resources/products/1/c_0001.jpg','2015-11-02',1,'小米 4c 标准版 全网通 白色 移动联通电信4G手机 双卡双待',1,'1'),('10','华为 Ascend Mate7',2699,2599,'resources/products/1/c_0010.jpg','2015-11-02',1,'华为 Ascend Mate7 月光银 移动4G手机 双卡双待双通6英寸高清大屏，纤薄机身，智能超八核，按压式指纹识别！!选择下方“移动老用户4G飞享合约”，无需换号，还有话费每月返还！',1,'1'),('11','vivo X5Pro',2399,2298,'resources/products/1/c_0014.jpg','2015-11-02',1,'移动联通双4G手机 3G运存版 极光白【购机送蓝牙耳机+蓝牙自拍杆】新升级3G运行内存·双2.5D弧面玻璃·眼球识别技术',1,'1'),('12','努比亚（nubia）My 布拉格',1899,1799,'resources/products/1/c_0013.jpg','2015-11-02',0,'努比亚（nubia）My 布拉格 银白 移动联通4G手机 双卡双待【嗨11，下单立减100】金属机身，快速充电！布拉格相机全新体验！',1,'1'),('13','华为 麦芒4',2599,2499,'resources/products/1/c_0012.jpg','2015-11-02',1,'华为 麦芒4 晨曦金 全网通版4G手机 双卡双待金属机身 2.5D弧面屏 指纹解锁 光学防抖',1,'1'),('14','vivo X5M',1899,1799,'resources/products/1/c_0011.jpg','2015-11-02',0,'vivo X5M 移动4G手机 双卡双待 香槟金【购机送蓝牙耳机+蓝牙自拍杆】5.0英寸大屏显示·八核双卡双待·Hi-Fi移动KTV',1,'1'),('15','Apple iPhone 6 (A1586)',4399,4288,'resources/products/1/c_0015.jpg','2015-11-02',1,'Apple iPhone 6 (A1586) 16GB 金色 移动联通电信4G手机长期省才是真的省！点击购机送费版，月月送话费，月月享优惠，畅享4G网络，就在联通4G！',1,'1'),('16','华为 HUAWEI Mate S 臻享版',4200,4087,'resources/products/1/c_0016.jpg','2015-11-03',0,'华为 HUAWEI Mate S 臻享版 手机 极昼金 移动联通双4G(高配)满星评价即返30元话费啦；买就送电源+清水套+创意手机支架；优雅弧屏，mate7升级版',1,'1'),('17','索尼(SONY) E6533 Z3+',4099,3999,'resources/products/1/c_0017.jpg','2015-11-02',0,'索尼(SONY) E6533 Z3+ 双卡双4G手机 防水防尘 涧湖绿索尼z3专业防水 2070万像素 移动联通双4G',1,'1'),('18','HTC One M9+',3599,3499,'resources/products/1/c_0018.jpg','2015-11-02',0,'HTC One M9+（M9pw） 金银汇 移动联通双4G手机5.2英寸，8核CPU，指纹识别，UltraPixel超像素前置相机+2000万/200万后置双镜头相机！降价特卖，惊喜不断！',1,'1'),('19','HTC Desire 826d 32G 臻珠白',1599,1469,'resources/products/1/c_0020.jpg','2015-11-02',1,'后置1300万+UltraPixel超像素前置摄像头+【双】前置扬声器+5.5英寸【1080p】大屏！',1,'1'),('2','中兴 AXON',2899,2699,'resources/products/1/c_0002.jpg','2015-11-05',1,'中兴 AXON 天机 mini 压力屏版 B2015 华尔金 移动联通电信4G 双卡双待',1,'1'),('20','小米 红米2A 增强版 白色',649,549,'resources/products/1/c_0019.jpg','2015-11-02',0,'新增至2GB 内存+16GB容量！4G双卡双待，联芯 4 核 1.5GHz 处理器！',1,'1'),('21','魅族 魅蓝note2 16GB 白色',1099,999,'resources/products/1/c_0021.jpg','2015-11-02',0,'现货速抢，抢完即止！5.5英寸1080P分辨率屏幕，64位八核1.3GHz处理器，1300万像素摄像头，双色温双闪光灯！',1,'1'),('22','三星 Galaxy S5 (G9008W) 闪耀白',2099,1999,'resources/products/1/c_0022.jpg','2015-11-02',1,'5.1英寸全高清炫丽屏，2.5GHz四核处理器，1600万像素',1,'1'),('23','sonim XP7700 4G手机',1799,1699,'resources/products/1/c_0023.jpg','2015-11-09',1,'三防智能手机 移动/联通双4G 安全 黑黄色 双4G美国军工IP69 30天长待机 3米防水防摔 北斗',1,'1'),('24','努比亚（nubia）Z9精英版 金色',3988,3888,'resources/products/1/c_0024.jpg','2015-11-02',1,'移动联通电信4G手机 双卡双待真正的无边框！金色尊贵版！4GB+64GB大内存！',1,'1'),('25','Apple iPhone 6 Plus (A1524) 16GB 金色',5188,4988,'resources/products/1/c_0025.jpg','2015-11-02',0,'Apple iPhone 6 Plus (A1524) 16GB 金色 移动联通电信4G手机 硬货 硬实力',1,'1'),('26','Apple iPhone 6s (A1700) 64G 玫瑰金色',6388,6088,'resources/products/1/c_0026.jpg','2015-11-02',0,'Apple iPhone 6 Plus (A1524) 16GB 金色 移动联通电信4G手机 硬货 硬实力',1,'1'),('27','三星 Galaxy Note5（N9200）32G版',5588,5388,'resources/products/1/c_0027.jpg','2015-11-02',0,'旗舰机型！5.7英寸大屏，4+32G内存！不一样的SPen更优化的浮窗指令！赠无线充电板！',1,'1'),('28','三星 Galaxy S6 Edge+（G9280）32G版 铂光金',5999,5888,'resources/products/1/c_0028.jpg','2015-11-02',0,'赠移动电源+自拍杆+三星OTG金属U盘+无线充电器+透明保护壳',1,'1'),('29','LG G4（H818）陶瓷白 国际版',3018,2978,'resources/products/1/c_0029.jpg','2015-11-02',0,'李敏镐代言，F1.8大光圈1600万后置摄像头，5.5英寸2K屏，3G+32G内存，LG年度旗舰机！',1,'1'),('3','华为荣耀6',1599,1499,'resources/products/1/c_0003.jpg','2015-11-02',0,'荣耀 6 (H60-L01) 3GB内存标准版 黑色 移动4G手机',1,'1'),('30','微软(Microsoft) Lumia 640 LTE DS (RM-1113)',1099,999,'resources/products/1/c_0030.jpg','2015-11-02',0,'微软首款双网双卡双4G手机，5.0英寸高清大屏，双网双卡双4G！',1,'1'),('31','宏碁（acer）ATC705-N50 台式电脑',2399,2299,'resources/products/1/c_0031.jpg','2015-11-02',0,'爆款直降，满千减百，品质宏碁，特惠来袭，何必苦等11.11，早买早便宜！',1,'2'),('32','Apple MacBook Air MJVE2CH/A 13.3英寸',6788,6688,'resources/products/1/c_0032.jpg','2015-11-02',0,'宽屏笔记本电脑 128GB 闪存',1,'2'),('32E8AC7F7460400BB331252FA7706F5D','ww',4,3,'resources/products/D/4/A51C472986C142968DF8FA4C94F6EF95.jpg','2020-04-25',1,'',1,'6'),('33','联想（ThinkPad） 轻薄系列E450C(20EH0001CD)',4399,4199,'resources/products/1/c_0033.jpg','2015-11-02',0,'联想（ThinkPad） 轻薄系列E450C(20EH0001CD)14英寸笔记本电脑(i5-4210U 4G 500G 2G独显 Win8.1)',1,'2'),('34','联想（Lenovo）小新V3000经典版',4599,4499,'resources/products/1/c_0034.jpg','2015-11-02',0,'14英寸超薄笔记本电脑（i7-5500U 4G 500G+8G SSHD 2G独显 全高清屏）黑色满1000減100，狂减！火力全开，横扫3天！',1,'2'),('35','华硕（ASUS）经典系列R557LI',3799,3699,'resources/products/1/c_0035.jpg','2015-11-02',0,'15.6英寸笔记本电脑（i5-5200U 4G 7200转500G 2G独显 D刻 蓝牙 Win8.1 黑色）',1,'2'),('36','华硕（ASUS）X450J',4599,4399,'resources/products/1/c_0036.jpg','2015-11-02',0,'14英寸笔记本电脑 （i5-4200H 4G 1TB GT940M 2G独显 蓝牙4.0 D刻 Win8.1 黑色）',1,'2'),('37','戴尔（DELL）灵越 飞匣3000系列',3399,3299,'resources/products/1/c_0037.jpg','2015-11-03',0,' Ins14C-4528B 14英寸笔记本（i5-5200U 4G 500G GT820M 2G独显 Win8）黑',1,'2'),('38','惠普(HP)WASD 暗影精灵',5699,5499,'resources/products/1/c_0038.jpg','2015-11-02',0,'15.6英寸游戏笔记本电脑(i5-6300HQ 4G 1TB+128G SSD GTX950M 4G独显 Win10)',1,'2'),('39','Apple 配备 Retina 显示屏的 MacBook',11299,10288,'resources/products/1/c_0039.jpg','2015-11-02',0,'Pro MF840CH/A 13.3英寸宽屏笔记本电脑 256GB 闪存',1,'2'),('4','联想 P1',2199,1999,'resources/products/1/c_0004.jpg','2015-11-02',0,'联想 P1 16G 伯爵金 移动联通4G手机充电5分钟，通话3小时！科技源于超越！品质源于沉淀！5000mAh大电池！高端商务佳配！',1,'1'),('40','机械革命（MECHREVO）MR X6S-M',6799,6599,'resources/products/1/c_0040.jpg','2015-11-02',0,'15.6英寸游戏本(I7-4710MQ 8G 64GSSD+1T GTX960M 2G独显 IPS屏 WIN7)黑色',1,'2'),('41','神舟（HASEE） 战神K660D-i7D2',5699,5499,'resources/products/1/c_0041.jpg','2015-11-02',0,'15.6英寸游戏本(i7-4710MQ 8G 1TB GTX960M 2G独显 1080P)黑色',1,'2'),('42','微星（MSI）GE62 2QC-264XCN',6199,5999,'resources/products/1/c_0042.jpg','2015-11-02',0,'15.6英寸游戏笔记本电脑（i5-4210H 8G 1T GTX960MG DDR5 2G 背光键盘）黑色',1,'2'),('43','雷神（ThundeRobot）G150S',5699,5499,'resources/products/1/c_0043.jpg','2015-11-02',0,'15.6英寸游戏本 ( i7-4710MQ 4G 500G GTX950M 2G独显 包无亮点全高清屏) 金',1,'2'),('44','惠普（HP）轻薄系列 HP',3199,3099,'resources/products/1/c_0044.jpg','2015-11-02',0,'15-r239TX 15.6英寸笔记本电脑（i5-5200U 4G 500G GT820M 2G独显 win8.1）金属灰',1,'2'),('45','未来人类（Terrans Force）T5',10999,9899,'resources/products/1/c_0045.jpg','2015-11-02',0,'15.6英寸游戏本（i7-5700HQ 16G 120G固态+1TB GTX970M 3G GDDR5独显）黑',1,'2'),('46','戴尔（DELL）Vostro 3800-R6308 台式电脑',3299,3199,'resources/products/1/c_0046.jpg','2015-11-02',0,'（i3-4170 4G 500G DVD 三年上门服务 Win7）黑',1,'2'),('46BDB7B03F9845D587E73B7F288AFF92','ww',4,3,'resources/products/1/6/06062643131341F7B369DD3404F16DC0.jpg','2020-04-25',1,'',1,'6'),('47','联想（Lenovo）H3050 台式电脑',5099,4899,'resources/products/1/c_0047.jpg','2015-11-11',0,'（i5-4460 4G 500G GT720 1G独显 DVD 千兆网卡 Win10）23英寸',1,'2'),('48','Apple iPad mini 2 ME279CH/A',2088,1888,'resources/products/1/c_0048.jpg','2015-11-02',0,'（配备 Retina 显示屏 7.9英寸 16G WLAN 机型 银色）',1,'2'),('49','小米（MI）7.9英寸平板',1399,1299,'resources/products/1/c_0049.jpg','2015-11-02',0,'WIFI 64GB（NVIDIA Tegra K1 2.2GHz 2G 64G 2048*1536视网膜屏 800W）白色',1,'2'),('5','摩托罗拉 moto x（x+1）',1799,1699,'resources/products/1/c_0005.jpg','2015-11-01',0,'摩托罗拉 moto x（x+1）(XT1085) 32GB 天然竹 全网通4G手机11月11天！MOTO X震撼特惠来袭！1699元！带你玩转黑科技！天然材质，原生流畅系统！',1,'1'),('50','Apple iPad Air 2 MGLW2CH/A',2399,2299,'resources/products/1/c_0050.jpg','2015-11-12',0,'（9.7英寸 16G WLAN 机型 银色）',1,'2'),('6','魅族 MX5 16GB 银黑色',1899,1799,'resources/products/1/c_0006.jpg','2015-11-02',0,'魅族 MX5 16GB 银黑色 移动联通双4G手机 双卡双待送原厂钢化膜+保护壳+耳机！5.5英寸大屏幕，3G运行内存，2070万+500万像素摄像头！长期省才是真的省！',1,'1'),('7','三星 Galaxy On7',1499,1398,'resources/products/1/c_0007.jpg','2015-11-14',0,'三星 Galaxy On7（G6000）昂小七 金色 全网通4G手机 双卡双待新品火爆抢购中！京东尊享千元良机！5.5英寸高清大屏！1300+500W像素！评价赢30元话费券！',1,'1'),('8','NUU NU5',1288,1190,'resources/products/1/c_0008.jpg','2015-11-02',0,'NUU NU5 16GB 移动联通双4G智能手机 双卡双待 晒单有礼 晨光金香港品牌 2.5D弧度前后钢化玻璃 随机附赠手机套+钢化贴膜 晒单送移动电源+蓝牙耳机',1,'1'),('8BD0778716CF40679834FDAADCAF53B7','11',11,11,'resources/products/2/F/0BED30FE20774DC898E1C881FF42CEE3.jpg','2020-04-25',1,'',1,'1'),('9','乐视（Letv）乐1pro（X800）',2399,2299,'resources/products/1/c_0009.jpg','2015-11-02',0,'乐视（Letv）乐1pro（X800）64GB 金色 移动联通4G手机 双卡双待乐视生态UI+5.5英寸2K屏+高通8核处理器+4GB运行内存+64GB存储+1300万摄像头！',1,'1'),('A33AD13D92004214A5AEAF400E40FF40','1111',1111,1111,'resources/products/B/4/37D63EF9DA0649AE9042E0CB127BD8E4.png','2020-04-25',1,'',1,'5'),('BE71C196238B4BB5B26762595EF74C9C','aa',1,1,'resources/products/0/8/0E7090A639CA4AE3A42379172431E351.jpg','2020-04-25',1,'',1,'6'),('F22CB97A07C24737BF07614906E3B489',NULL,NULL,NULL,NULL,'2020-04-25',NULL,NULL,1,NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` varchar(32) NOT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `securitycode` varchar(64) DEFAULT NULL,
  `remark` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
