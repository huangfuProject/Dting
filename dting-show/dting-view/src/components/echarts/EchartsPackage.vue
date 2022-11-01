<template>
    <div :style="style"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    props: {
        height: {
            type:String,
            default: ""
        },
        width: {
            type:String,
            default: ""
        },
        options: {}
    },
    data(){
        return {
            //echart对象
            myChart:null
        }
    },
    //挂载时调用
    mounted(){
        //HTMLElement
        this.myChart = echarts.init(this.$el);
        // 使用刚指定的配置项和数据显示图表。
        this.myChart.setOption(this.options);
    },
    //计算属性  内部的计算值 可直接被外部绑定使用
    computed:{
        style(){
            return {
                    width: this.width?this.width:'600px',
                    height: this.height?this.height:'300px'
            }
        }
    },
    // 数据监听
    watch:{
        style(){
            if(this.myChart){
                this.myChart.resize({
                    animation: {
                        duration: 400
                    }
                });
            }
        },
        options(){
            if(this.myChart){
                this.myChart.setOption(this.options)
            }
        }
    }
}
</script>
