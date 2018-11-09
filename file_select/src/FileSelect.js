/**
 *  图片选择组件.
 */

import FileSelect from '@/components/FileSelect/FileSelect'

if (typeof window !== 'undefined' && window.Vue) {
    window.Vue.component('FileSelect', FileSelect)
}
// 定义一个 mixin 对象.所有 调用 FileSelect 组件,最好依赖此混入对象.
let FileSelectMixin = {
    data () {
        return {
            /**
             * FileSelect 值 监听这个值 的 变化,如果变为了 true,那么 FileSelect 执行 回显数据 操作.
             * 这个值添加时不需要,修改时需要.
             */
            fileSelectTrigger: false
        }
    }

}

export {FileSelect, FileSelectMixin}

