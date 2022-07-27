const input = document.querySelector('.input');

input.addEventListener('change', function(){
    if(this.value == null){
            return;
    }

    fetch('/getData?id=' + this.value)
       .then(function(response) {
        return response.json();
       })
      .then(function(body){
        if(!body){
            return;
        }

        if(body.radius != null){
            let radius = body.radius;
            let points = body.points;

            for(const point of points){
                let lat = point.lat;
                let lng = point.lng;

                var poi = new BMap.Point(lng,lat);
                map.centerAndZoom(poi, 20);

                //画圆
                var circle = new BMap.Circle(poi, radius);
                circle.setFillColor("#A6CBA1"); //填充颜色
                circle.setStrokeColor("red"); //边线颜色
                map.addOverlay(circle);

            }
        }else{

            let points = body.points;
            var arrPois = [];
            for(var i = 0 ;i< points.length;i++){

                var lng =points[i].lng;
                var lat =points[i].lat;
                arrPois.push(new BMap.Point(lng,lat));
            }

            var poi = new BMap.Point(points[0].lng, points[0].lat);
            map.centerAndZoom(poi, 16);
            arrPois.push(poi);
            var polyline = new BMap.Polyline(arrPois, {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5});
            polyline.setFillColor("#A6CBA1"); //填充颜色
            map.addOverlay(polyline);

        }
      });

})


