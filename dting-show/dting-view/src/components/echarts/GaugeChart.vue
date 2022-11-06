<template>
    <div>
        <!--Dting使用的环状图相关-->
        <EchartsPackage :options="optionData" :height="height" :width="width"/>
    </div>
</template>

<script>
import EchartsPackage from './EchartsPackage.vue'
import * as echarts from 'echarts';
export default {
    name: "GaugeChart",
    props:{
        //最大值
        serviceData: [],
        height: {
            type:String,
            default: "100px"
        },
        width: {
            type:String,
            default: "100px"
        }
    },
    data(){
        return {

        }
    },
    methods:{
        
    },
    computed: {
        /**
         * 资源用量
         */
        optionData(){
            return {
        series: [
          {
            center: ['50%', '46%'],
             radius: '90%',
            type: 'gauge',
            startAngle: 220,
            endAngle: -40,
            min: 0,
            max: 100,
            roundCap: true,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#297FFE' }, //柱图渐变色
                { offset: 1, color: '#08E5FF' } //柱图渐变色
              ]),
              shadowColor: '#297FFE',
              shadowBlur: 10,
              shadowOffsetX: 2,
              shadowOffsetY: 2
            },
            progress: {
              // show:false,
              roundCap: true,
              show: true,
              width: 12
            },
            axisLine: {
              roundCap: true,
              lineStyle: {
                width: 12
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: false
            },
            axisLabel: {
              show: false
            },
            pointer: {
              show: false
            },

            detail: {
              valueAnimation: true,
              fontSize: 80,
              offsetCenter: [0, '0%'],
              formatter: function(value) {
                return '{value|' + value + '}{unit|%}'
              },
              rich: {
                value: {
                  fontSize: 20,
                  fontWeight: 'bolder',
                  color: '#15A9F1'
                },
                unit: {
                  fontSize: 15,
                  color: '#15A9F1',
                  padding: [0, 0, -10, 10]
                }
              }
            },
            data: this.serviceData,
            title: {
              color: '#00EBEE',
              fontSize: 12,
              offsetCenter: [0, '80%']
            }
          }
        ]
      }
        },
    },
    components:{
        EchartsPackage
    }
}
</script>
