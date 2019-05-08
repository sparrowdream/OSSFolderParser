# OSSFolderParser
A parser for OSS objects. Transform objects into a logic tree.<br>
阿里云 OSS 服务因为分布式缘故，文件夹在其中存放并非以物理形式，而是逻辑形式<br>
其文件夹的显示以 0k 文件形式，例如：<br>
&nbsp;&nbsp;index.html<br>
&nbsp;&nbsp;static/<br>
&nbsp;&nbsp;static/css/<br>
&nbsp;&nbsp;static/css/app.440c74b6d85269a2ef9b5794fd607aac.css<br>
&nbsp;&nbsp;static/css/app.440c74b6d85269a2ef9b5794fd607aac.css.map<br>
&nbsp;&nbsp;static/fonts/<br>
&nbsp;&nbsp;static/fonts/roboto-latin-100.987b845.woff2<br>
&nbsp;&nbsp;static/fonts/roboto-latin-100.e9dbbe8.woff<br>
&nbsp;&nbsp;...<br>
当我们从其中读取时，需要使用原先的文件夹中有文件和文件夹形式就变得困难<br>
本项目再一个课程设计中催生，解决该问题，可将上述内容转为相对应的逻辑目录树<br>
下文是树打印内容：<br>
/<br>
&nbsp;|- index.html<br>
&nbsp;|- static/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- css/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- app.440c74b6d85269a2ef9b5794fd607aac.css.map<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- app.440c74b6d85269a2ef9b5794fd607aac.css<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- fonts/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- roboto-latin-100.e9dbbe8.woff<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|- roboto-latin-900italic.7b770d6.woff2<br>
...<br>
