## CS209A 2020Spring Final Project Document

### 要求
1. 1Page = 450 – 650 words
2. Short description of RESTful interface is  correct
3. Short description of Model View  Controller design is reasonable
4. To get full marks the descriptions should  refer to the problem in the assignment 
(not just be general abstract textbook  type descriptions).

### 文档中文版本.
1. RESTFUL 说到底是Representational State Transfer的缩写,第一个单词表现层的含义是对应的资源,一个实体,或者说一个具体信息.而在这个project中的体现就是files,文件.而后面两个单词是连在一起的,意味着状态转化.由于前端后端要考虑多客户端的问题,并且是通过无状态的HTTP协议进行通信,所以不能以面向过程的方式去设计,要将后端设计成一个状态机,和HTTP协议的特性相适合.
2. 而具体来讲,就是以URL代表资源(这里就是/files前缀),客户端与服务器之间通过无状态的HTTP协议来通信,以HTTP的GET,PUT,POST,DELETE来使后端的状态发生变化,利用HTTP的错误码来表示错误.主要的设计有  
  + 检测存在:`/files/:md5/exists`,使用GET,返回`{"code": 0,"message": "","result": { "exists": true}}`格式的Json.
  + 文件比较:`/files/:md51/compare/:md5_2`,使用GET,返回`{"code": 0,"message": "","result": {"success": true}}`格式的Json.
  + 文件上传:`/files/:md5`,使用POST,返回`{"code": 0, "message": "","result": {"simple_similarity": 0.925,"levenshtein_distance": 18}}`格式的Json.
  + 文件下载:`/files/:md5`,使用GET.返回`{"code": 0, "message": "","result": {"Content":"test datas""}}`格式的Json.
  + 获取文件列表:`/files`,使用GET.返回`{"code": 0, "message": "","result": {"files": [{"md5": "fac27083ec676a0999e6d22e4c40f31a","length": 20,"preview": "这是一"},{"md5": "aa1b5fccb80521924433a5cea41dce3b","length": 1000,"preview": "这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！这是一段超长文本！！"}]}}`格式的文本.
3. MVC层面的设计,首先,服务器端要解析来的请求,这一方面属于Router层面,让每一个URl都有代码相应;接下来每段URL对应的代码就属于Controller层面,从Router层面接受数据,处理之后返回给用户;而处理的这一部分必然要接触到数据库,这一部分最好抽象出来,这就是Model层;由于本项目中单纯的是命令行结构,所以只是接收到Controller的数据并将其转化成Json等格式.而把MVC的设计和service,dao,model相对应起来,显然是Router交给Server,controller对应service,model对应dao和model,
而view层没有实现的必要,所以忽略.Router将请求转发给各个Controller,controller再调用model中的指令获取结果,最终组织成Json,发送回去.
4. 更具体来讲,将所有的数据库操作封装在dao中,所有的数据操作封装在model层,供service(controller)调用,再将service端获取的数据转化成能被传回的json格式的HTTP格式.在Server中将URL和Service绑定,就结束了.
5. 到实现层面,检测存在在本地转化MD5,然后发送MD5到服务器上;文件比较先本地转化为MD5,然后上传md5来比较;文件上传服务器端也要检验数据的完整性与可靠性;文件下载与获取文件列表较为简单.
6. 莱文斯坦距离直接使用了Apache提供的库,原理就不说了.

### Document English Version.
1. RESTFUL is the abbreviation of Representational State Transfer in the end, the meaning of the first word presentation layer is the corresponding resource, an entity, or a specific information. And the manifestation in this project is files, files. And the latter two The words are connected together, which means state transition. Because the front-end and back-end have to consider the problem of multiple clients and communicate through the stateless HTTP protocol, they cannot be designed in a process-oriented manner. A state machine, suitable for the characteristics of the HTTP protocol.
2. Specifically, the URL represents the resource (here is the / files prefix), and the client and server communicate through a stateless HTTP protocol, and HTTP GET, PUT, POST, DELETE to make the backend state Changes occur, using HTTP error codes to indicate errors. The main design is
  + Detect existence: `/files/:md5/exists`, use GET, return Json in` {"code": 0, "message": "", "result": {"exists": true}} `format
  + File comparison: `/files/:md51/compare/:md5_2`, use GET, return` {"code": 0, "message": "", "result": {"success": true}} `format Json.
  + File upload: `/files/:md5`, use POST, return` {"code": 0, "message": "", "result": {"simple_similarity": 0.925, "levenshtein_distance": 18}} ` Json in format.
  + File download: `/files/:md5`, use GET. Return` {"code": 0, "message": "", "result": {"Content": "test datas" "}}` format Json.
  + Get file list: `/files`, use GET. Return` {"code": 0, "message": "", "result": {"files": [{"md5": "fac27083ec676a0999e6d22e4c40f31a", "length ": 20," preview ":" This is a "}, {" md5 ":" aa1b5fccb80521924433a5cea41dce3b "," length ": 1000," preview ":" This is a long text !!! This is a long text! This is a long text !!! This is a long text !!! This is a long text !!! This is a long text !!! This is a long text !!! This is a long text !!! It is a long text !!! This is a long text !!! "}]}}` formatted text.
3. The design of the MVC level. First, the request to be parsed by the server side belongs to the Router level, so that each URl has a code corresponding; then the code corresponding to each piece of URL belongs to the Controller level, which is accepted from the Router level The data is processed and returned to the user; and this part of the processing must inevitably come into contact with the database. This part is best abstracted out, which is the Model layer; because the project is purely a command line structure, so only the data of the Controller Convert it to Json and other formats. The design of MVC corresponds to service, dao, and model. Obviously, the Router hands it to the server, the controller corresponds to service, and the model corresponds to dao and model.
The view layer is not necessary to implement, so ignore it. Router forwards the request to each Controller, and the controller calls the instructions in the model to obtain the result, and finally organizes it into Json and sends it back.
4. More specifically, all database operations are encapsulated in dao, all data operations are encapsulated in the model layer for service (controller) to call, and convert the data obtained by the service side to be returned The HTTP format of the json format. Bind the URL and Service in the Server and it is over.
5. At the implementation level, detect the existence of local conversion MD5, and then send MD5 to the server; the file comparison is first converted locally to MD5, and then uploaded to md5 for comparison; the file upload server also checks the integrity and reliability of the data; the file Downloading and obtaining file lists is relatively simple.
6. Levinstein distance directly uses the library provided by Apache, the principle will not introduce.