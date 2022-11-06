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
        legendData:[],
        serviceData: [],
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
    methods:{

        legendDataHandler(legendData){
            var data = []
            for(var i=0;i<legendData.length;i++){
                if (i%2==0) {
                    data.push("")
                }
                data.push(this.legendData[i]);
            }

            return data
        },
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
                data: this.legendDataHandler(this.legendData)
                },
                grid: {
                    top: 80
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
                series: this.serviceData
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
