const clearIcon = document.querySelector('.clear');
// 百度地图API功能
    var map = new BMap.Map('map');
    var poi = new BMap.Point(118.937284,32.119823);
    map.centerAndZoom(poi, 16);
    map.enableScrollWheelZoom();

    $("getLastOverLay").onclick = function(){
        if(overlays.length){
            alert(overlays[overlays.length - 1]);
        }else{
            alert("没有覆盖物");
        }
    }

    //信息窗口的内容定义
    var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
                    '地址：北京市海淀区上地十街10号<br/>电话：(010)59928888<br/>简介：百度大厦位于北京市海淀区西二旗地铁站附近，为百度公司综合研发及办公总部。' +
                  '</div>';

    //创建带信息窗口的poi点
    var searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
        title  : "百度大厦",      //标题
        width  : 290,             //宽度
        height : 105,              //高度
        panel  : "panel",         //检索结果面板
        enableAutoPan : true,     //自动平移
        searchTypes   :[
            BMAPLIB_TAB_SEARCH,   //周边检索
            BMAPLIB_TAB_TO_HERE,  //到这里去
            BMAPLIB_TAB_FROM_HERE //从这里出发
        ]
    });

    var overlays = [];
    //回调获得覆盖物信息
    var overlaycomplete = function(e){
        overlays.push(e.overlay);
        var result = "";
        result = "<p>";
        result += e.drawingMode + ":";
        if (e.drawingMode == BMAP_DRAWING_MARKER) {
            result += ' 坐标：' + e.overlay.getPosition().lng + ',' + e.overlay.getPosition().lat;
            if ($('isInfowindow').checked) {
                searchInfoWindow.open(e.overlay);
            }
        }
        if (e.drawingMode == BMAP_DRAWING_CIRCLE) {
            result += ' 半径：' + e.overlay.getRadius();
            result += ' 中心点：' + e.overlay.getCenter().lng + "," + e.overlay.getCenter().lat;
        }
        if (e.drawingMode == BMAP_DRAWING_POLYLINE || e.drawingMode == BMAP_DRAWING_POLYGON || e.drawingMode == BMAP_DRAWING_RECTANGLE) {
            result += ' 所画的点个数：' + e.overlay.getPath().length;
        }
        result += "</p>";
        $("showOverlayInfo").style.display = "none";
        $("panel").innerHTML += result; //将绘制的覆盖物信息结果输出到结果面板
    };

    var styleOptions = {
        strokeColor:"red",    //边线颜色。
        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3,       //边线的宽度，以像素为单位。
        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid' //边线的样式，solid或dashed。
    }
    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: false, //是否开启绘制模式
        enableDrawingTool: true, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
            offset: new BMap.Size(5, 5), //偏离值
            scale: 0.8 //工具栏缩放比例
        },
        circleOptions: styleOptions, //圆的样式
        polylineOptions: styleOptions, //线的样式
        polygonOptions: styleOptions, //多边形的样式
        rectangleOptions: styleOptions //矩形的样式
    });


    //添加鼠标绘制工具监听事件，用于获取绘制结果
    drawingManager.addEventListener('overlaycomplete', overlaycomplete);


    function $(id){
        return document.getElementById(id);
    }


clearIcon.addEventListener('click', function(){
console.log("click");
for(var i = 0; i < overlays.length; i++){
            map.removeOverlay(overlays[i]);
        }
        overlays.length = 0
});


    var isPanelShow = false;
    //显示结果面板动作
    $("showPanelBtn").onclick = showPanel;
    function showPanel(){
        if (isPanelShow == false) {
            isPanelShow = true;
            $("showPanelBtn").style.right = "230px";
            $("panelWrap").style.width = "230px";
            $("map").style.marginRight = "230px";
            $("showPanelBtn").innerHTML = "隐藏绘制结果信息<br/>>";
        } else {
            isPanelShow = false;
            $("showPanelBtn").style.right = "0px";
            $("panelWrap").style.width = "0px";
            $("map").style.marginRight = "0px";
            $("showPanelBtn").innerHTML = "显示绘制结果信息<br/><";
        }
    }

    //上传画圈的数据
    function circleData(lag, lat, radius){

    }