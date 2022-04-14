# 文本文件预处理

## 情景

作为一名 NLP 工程师，你打算从互联网上收集一些数据来训练自己的模型，于是你拜托了自己的爬虫工程师好友。几天后，你收到了一个装满了文本文件的压缩包。然而似乎这位好友不太靠谱，压缩包中文本文件的编码并不一致，也没有数据来源的相关描述。在几封邮件过后，你总算拿到了一个描述了数据源信息的 CSV 文件。现在你总算可以开始整理这堆数据，进行一些预处理步骤，来准备之后的训练了。

## 任务

编写一个文本文件预处理工具，其可以通过配置文件（YAML 格式）自动完成预处理过程（转换编码、整理目录树、输出处理摘要）。

你需要完成以下任务：

* 读取 YAML 格式的配置文件
* 将所有文本文件转换至目标编码
* 根据配置文件，将转换后的文本文件至于正确的分类层级形成的目录树下，并加上需要的前后缀
* 如果配置文件中有定义，输出转换过程的摘要。

### 读取配置文件

一个可能的 YAML 配置文件如下所示：

```yaml
Prefix: hi_
Postfix: _there
OutputPath: D:\tmp\txtout
InputCsvPath: D:\tmp\java2problem1\input.csv
InputTxtPath: D:\tmp\java2problem1\indata
TargetEncoding: UTF-8
CategorizeBy: 
    - Field
    - Source
    - Date
SummaryPath: D:\tmp\java2problem1\newout.csv
```

配置文件的路径，在程序运行时从命令行参数（args[0]）读入。若配置文件未提供，应抛出 NullPointerException。若配置文件不存在，应抛出 IllegalArgumentException。

不同的配置项有不同的默认值，具体如下：

* Prefix, Postfix 默认值为空
* TargetEncoding 默认值为 UTF-8
* 如果配置文件中未定义 SummaryPath，则无需输出摘要
* 如果配置文件中未定义 CategorizeBy，则直接将所有文件至于 OutputPath 下
* 如果配置文件中未定义 InputCsvPath, InputTxtPath, OutputPath，应抛出 NullPointerException。
* 如果配置文件中定义的 InputCsvPath, InputTxtPath, OutputPath 为无效路径，应抛出 IllegalArgumentException。

可认为配置文件的编码为 UTF-8。

> 注：InputCsvPath, InputTxtPath, OutputPath 最后无文件系统分隔符。

### 转换编码

将输入的 CSV 文件中的所有文本文件，转换至 TargetEncoding 设定的编码。

可认为输入 CSV 文件的编码为 UTF-8。

各文本文件都位于 InputTxtPath 下，文件名为 filename 列内容。

若读取文件过程中发现文件不存在，应抛出 IllegalArgumentException。

一个可能的输入 CSV 文件如下所示：

```
filename,source,field,date
1.txt,人日,时政,2020/3/5
2.txt,天涯,生活,2020/3/1
3.txt,腾讯,体育,2020/1/9
4.txt,新浪,科技,2020/2/25
5.txt,人日,人文,2020/2/28
6.txt,天涯,时政,2020/2/13
7.txt,腾讯,生活,2020/2/1
8.txt,新浪,体育,2020/3/5
9.txt,人日,科技,2020/1/6
10.txt,天涯,人文,2019/1/8
11.txt,腾讯,时政,2020/1/5
12.txt,新浪,生活,2019/12/31
13.txt,人日,体育,2019/12/1
14.txt,天涯,科技,2019/1/5
15.txt,腾讯,人文,2019/5/6
```

csv文件的前4列将如上图所示，后部可能跟有无意义的列

### 层级、目录树、前后缀

根据 CategorizeBy 设定，将文本文件根据其在输入 CSV 文件中描述的元数据，置于 OutputPath 中相应的目录树下，并在文件名加上前后缀。

所有可能的 CategorizeBy 值如下：

* Field：所属领域
* Source：来源网站
* Date：抓取日期
  * 需要按照抓取日和程序运行日的日期差，分为如下几类：一周内 (0~7)，一月内 (8~30)，半年内 (31~180)，半年以上 (180+)

以上文示例配置文件为例，应生成如下目录树。

```
.
├── 人文
│   ├── 人日
│   │   └── 一周内
│   │       └── hi_5_there.txt
│   ├── 天涯
│   │   └── 半年以上
│   │       └── hi_10_there.txt
│   └── 腾讯
│       └── 半年以上
│           └── hi_15_there.txt
├── 体育
│   ├── 人日
│   │   └── 半年内
│   │       └── hi_13_there.txt
│   ├── 新浪
│   │   └── 一周内
│   │       └── hi_8_there.txt
│   └── 腾讯
│       └── 半年内
│           └── hi_3_there.txt
├── 时政
│   ├── 人日
│   │   └── 一周内
│   │       └── hi_1_there.txt
│   ├── 天涯
│   │   └── 一月内
│   │       └── hi_6_there.txt
│   └── 腾讯
│       └── 半年内
│           └── hi_11_there.txt
├── 生活
│   ├── 天涯
│   │   └── 一周内
│   │       └── hi_2_there.txt
│   ├── 新浪
│   │   └── 半年内
│   │       └── hi_12_there.txt
│   └── 腾讯
│       └── 半年内
│           └── hi_7_there.txt
└── 科技
    ├── 人日
    │   └── 半年内
    │       └── hi_9_there.txt
    ├── 天涯
    │   └── 半年以上
    │       └── hi_14_there.txt
    └── 新浪
        └── 一月内
            └── hi_4_there.txt
```

### 生成摘要

如果配置文件中定义了 SummaryPath，则需要输出转换过程中的摘要至此路径。

摘要文件的编码格式为 UTF-8。

以下为上文示例生成的摘要。

```
path,source,field,date,char_count,old_encoding
D:\tmp\txtout\hi_1_there.txt,人日,时政,20200305,1447,UTF-8
D:\tmp\txtout\hi_2_there.txt,天涯,生活,20200301,1448,UTF-8
D:\tmp\txtout\hi_3_there.txt,腾讯,体育,20200109,1448,UTF-16LE
D:\tmp\txtout\hi_4_there.txt,新浪,科技,20200225,1448,UTF-16BE
D:\tmp\txtout\hi_5_there.txt,人日,人文,20200228,1447,GB18030
D:\tmp\txtout\hi_6_there.txt,天涯,时政,20200213,1447,GB18030
D:\tmp\txtout\hi_7_there.txt,腾讯,生活,20200201,1447,GB18030
D:\tmp\txtout\hi_8_there.txt,新浪,体育,20200305,1447,Big5
D:\tmp\txtout\hi_9_there.txt,人日,科技,20200106,0,UTF-8
D:\tmp\txtout\hi_10_there.txt,天涯,人文,20190108,0,UTF-8
D:\tmp\txtout\hi_11_there.txt,腾讯,时政,20200105,0,UTF-8
D:\tmp\txtout\hi_12_there.txt,新浪,生活,20191231,0,UTF-8
D:\tmp\txtout\hi_13_there.txt,人日,体育,20191201,0,UTF-8
D:\tmp\txtout\hi_14_there.txt,天涯,科技,20190105,0,UTF-8
D:\tmp\txtout\hi_15_there.txt,腾讯,人文,20190506,0,UTF-8
```



## 其他信息

1. 本题中涉及的编码由且仅有：UTF-8, UTF-8 With BOM, UTF-16 LE, UTF-16 BE, GBK, GB2312, GB18030
2. 除上文提及的异常需要处理外，可假定其他输入都合法且不会引发异常
3. 在摘要文件中，输出的 old_encoding （原始编码）列不需要完全准确。
    * 对 GBK, GB2312, GB18030 可统一识别为 GB18030
    * 对 UTF-8 With BOM 可以识别为 UTF-8
    * 对其他编码应正确识别



