<template>
    <div>
        <!--系统内存监控折线图-->
        <div><EchartsPackage :options="option"/></div>
    </div>
</template>

<script>
import EchartsPackage from '../../components/echarts/EchartsPackage.vue'
export default {
    name: "SystemMonitors",
    data(){
        return {
            systemXAxisArray:["2022/10/26 8:45","2022/10/26 8:46","2022/10/26 8:47","2022/10/26 8:48","2022/10/26 8:49","2022/10/26 8:50","2022/10/26 8:51"],
            systemUseMemory:[15000, 10000, 8000, 2000, 6000, 4000, 10000],
            systemMaxMemory:[20000, 20000, 20000, 20000, 20000, 20000, 20000]
        }
    },
    computed: {
        option(){
            return {
                title: {
                text: '系统内存监控'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                data: ['系统总内存','系统已经使用的内存']
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: this.systemXAxisArray
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '系统总内存',
                        type: 'line',
                        step:true,
                        data: this.systemMaxMemory
                    },
                    {
                        name: '系统已经使用的内存',
                        type: 'line',
                        step:false,
                        data: this.systemUseMemory
                    }
                ]
            }
        }
    },
    components:{
        EchartsPackage
    }
}
</script>
