import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'EnvContainer',
    component: ()=>import("../views/env/EnvContainer.vue")
  },{
      path: '/system/monitors',
      name: "SystemMonitors",
      component: ()=>import("../views/monitors/SystemMonitors.vue")
  }
]

const router = new VueRouter({
  mode:'history',//去端口#号,
  routes
})

export default router