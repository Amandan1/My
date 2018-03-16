<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%String contextPath = request.getContextPath();%>

<script type="text/javascript" src="<%=contextPath%>/jslib/echarts.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/jslib/jquery-2.0.3.js" charset="utf-8"></script>


<script type="text/javascript" charset="utf-8">

    myhope.contextPath = '<%=contextPath%>';

    var myChart = echarts.init(document.getElementById('main_p3'));
    var option = {
        title: {
            text: ''
        },

        tooltip : {
            show : true
        },
        legend : {
            data : []
        },
        xAxis : [ {
            type : 'category'

        } ],
        yAxis : [ {
            type : 'value'
        } ],
        series : [ {
            name : '',
            type : 'bar'
        } ]
    };
    //加载数据到option
    loadData(option);
    //设置option
    myChart.setOption(option);


    function loadData(option) {
        $.ajax({
            type : 'post',	//传输类型
            async : false,	//同步执行
            url : myhope.contextPath + '/portal/portal!nsc_getOrderAmount.myhope',
            data : {},
            dataType : 'json', //返回数据形式为json
            success : function(result) {
                if (result) {
                    //初始化xAxis[0]的data
                    option.xAxis[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.xAxis[0].data.push(result[i].month);
                    }
                    //初始化series[0]的data
                    option.series[0].data = [];
                    for (var i=0; i<result.length; i++) {
                        option.series[0].data.push(result[i].monthAmount);
                    }
                }
            },
            error : function(errorMsg) {
                alert("加载数据失败");
            }
        });//AJAX
    }


</script>


<div id="main_p3" style="width: 800px;height:300px;">
</div>