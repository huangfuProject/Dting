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
        xAxisArray:[],
        use:[],
        max:[],
        lineChartTitle:{
            type: String,
            default:""
        },
        height: {
            type:String,
            default: ""
        },
        width: {
            type:String,
            default: ""
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
                    data: this.xAxisArray,
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
                        data: this.max,
                        showSymbol: false
                    },
                    {
                        name: '已使用',
                        type: 'line',
                        step:false,
                        data: this.use,
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
        xAxisArray(){
            //动态变更首尾数据
            this.optionData.xAxis.axisLabel.interval=this.xAxisArray.length-2
        }
    }
}
</script>
