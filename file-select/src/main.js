// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import 'bootstrap/dist/css/bootstrap.css'

Vue.use(ElementUI)

Vue.config.productionTip = false


//import Vue from 'vue'


//import Vue from 'vue'
/*import preview from 'vue-photo-preview'
import 'vue-photo-preview/dist/skin.css'

var opt = {
    fullscreenEl: true,
    bgOpacity: 0.7
}
Vue.use(preview)*/


/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>'
})


