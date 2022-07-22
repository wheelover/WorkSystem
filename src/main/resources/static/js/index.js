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

        let radius = body.radius;
        let points = body.points;

        for(const point of points){
            let lat = point.lat;
            let lng = point.lng;

            var poi = new BMap.Point(lng,lat);
            map.centerAndZoom(poi, 16);

            //画圆
            var circle = new BMap.Circle(poi, radius);
            circle.setFillColor("#A6CBA1"); //填充颜色
            circle.setStrokeColor("red"); //边线颜色
            map.addOverlay(circle);

        }

      });

})


