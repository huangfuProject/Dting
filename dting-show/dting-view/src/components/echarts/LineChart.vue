<template>
    <div>
        <!--Dting使用的折线图相关-->
        <EchartsPackage :options="optionData" :height="height" :width="width"/>
    </div>
</template>

<script>
import EchartsPackage from './EchartsPackage.vue'
export default {
    name: "LineChart",
    props:{
        systemXAxisArray:[],
        systemUse:[],
        systemMax:[],
        lineChartTitle:{
            type: String,
            default:""
        },
        height: {
            type:String,
            default: "600px"
        },
        width: {
            type:String,
            default: "800px"
        }
    },
    computed: {
        /**
         * 资源用量
         */
        optionData(){
            return {
                title: {
                text: this.lineChartTitle
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                data: ['总量','已使用']
                },
                xAxis: {
                    type: 'category',
                    show: true,
                    boundaryGap: false,
                    data: this.systemXAxisArray,
                    axisLabel: {
                        interval: 2
                    }
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '总量',
                        type: 'line',
                        step:true,
                        data: this.systemMax,
                        showSymbol: false
                    },
                    {
                        name: '已使用',
                        type: 'line',
                        step:false,
                        data: this.systemUse,
                        showSymbol: false
                    }
                ]
            }
        }
    },
    components:{
        EchartsPackage
    },
    watch: {
        systemXAxisArray(){
            //动态变更首尾数据
            this.optionData.xAxis.axisLabel.interval=this.systemXAxisArray.length-2
        }
    }
}
</script>
