<template>
  <div class="home">
    <EchartsPackage />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import EchartsPackage from "@/components/echarts/EchartsPackage.vue";

@Options({
  components: {
    EchartsPackage 
  }
})
export default class Home extends Vue {}
</script>
