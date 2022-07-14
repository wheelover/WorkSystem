
    window.onload=function () {
      var oc=document.querySelector('canvas')
      if(oc.getContext){
        var ctx=oc.getContext('2d')

        var arr=[];

        setInterval(function () {
          ctx.clearRect(0,0,oc.width,oc.height);
          for (let i = 0; i < arr.length; i++) {
              arr[i].deg+=7;
              arr[i].x=arr[i].startX + Math.sin(arr[i].deg*Math.PI/180)*arr[i].step*2;
              arr[i].y=arr[i].startY - (arr[i].deg*Math.PI/180)*arr[i].step;
              if(arr[i].y<=50){
                arr.splice(i,1);
            }
          }




          //绘制
          for (let i = 0; i < arr.length; i++) {
            ctx.save();
            ctx.fillStyle="rgba("+arr[i].red+","+arr[i].green+","+arr[i].blue+","+arr[i].alp+")";
            ctx.beginPath();
            ctx.arc(arr[i].x,arr[i].y,arr[i].r,0,2*Math.PI);
            ctx.fill();
            ctx.restore();
          }
        },1000/60)








        setInterval(function () {
          var r=Math.random()*6+2;
          var x=Math.random()*oc.width;
          var y=oc.height-r;
          var red=Math.round(Math.random()*255);
          var green=Math.round(Math.random()*255);
          var blue=Math.round(Math.random()*255);
          var alp=1;


          var deg=0;
          var startX=x;
          var startY=y;
          var step=Math.random()*20+10;

          arr.push({
            x,y,r,red,green,blue,alp,deg,startX,startY,step
          })
        },100)

      }
    }
