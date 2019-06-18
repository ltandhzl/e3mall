<html>
   <head>student</head>
   <body>
        string对象：姓名是:${name}<br>
        student对象:学号-${student.id},姓名-${student.name},年龄-${student.age},住址-${student.address}
        list对象：<br>
        <table border="1">
            <thead>
               <tr>
                   <th>序号</th>
                   <th>学号</th>
                   <th>姓名</th>
                   <th>年龄</th>
                   <th>住址</th>
               </tr>
            </thead>
            <tbody>
              <#list list as stu>
                  <#if stu_index%2==0>
                      <tr bgcolor="red">
                  <#else>
                      <tr bgcolor="#faebd7">
                  </#if>
                      <td>${stu_index}</td>
                      <td>${stu.id}</td>
                      <td>${stu.name}</td>
                      <td>${stu.age}</td>
                      <td>${stu.address}</td>
                  </tr>
              </#list>

            </tbody>
        </table><br>
<#--        ?date,?time,?datetime-->
       日期对象：${date?datetime}<br>
<#--       null值：${empty!"1234"}-->
        null值：
        <#if empty??>
            1234
            <#else >
            4321
        </#if>

   </body>
</html>
