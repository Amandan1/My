<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%String contextPath = request.getContextPath();%>

<script type="text/javascript" src="<%=contextPath%>/jslib/echarts.min.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/jslib/jquery-2.0.3.js" charset="utf-8"></script>


<script type="text/javascript" charset="utf-8">
    myhope.contextPath = '<%=contextPath%>';

    var myChart_p1 = echarts.init(document.getElementById('main_p1'));

    // 显示标题，图例和空的坐标轴
    myChart_p1.setOption({
        title: {
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} {b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series: [
            {
                name: '',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });
    myChart_p1.showLoading();// 加载动画
    // 异步加载数据
    $.ajax({
        url:/* myhope.contextPath + '/portal/portal!nsc_getGoodsSale.myhope' */,
        openType:'post',
        dataType:'json',
        success : function(data){
            var array = [];
            for (var i = 0; i < 5; i++) {
                var map = {};
                map.name = data.name[i];
                map.value = data.sale[i];
                array[i] = map;
            }
            myChart_p1.hideLoading();
            // 填入数据
            myChart_p1.setOption({
                legend: {
                    data: data.name
                },
                series: [{
                    // 根据名字对应到相应的系列
                    name: '',
                    type: 'pie',
                    data: array
                }],
            });
        },
        error : function(){

        }
    });


</script>


<div id="main_p1" style="width: 800px;height:300px;">
</div>