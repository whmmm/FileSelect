/**
 *  图片选择组件.
 */

import FileSelectWrapper from '@/components/FileSelect/FileSelectWrapper'

if (typeof window && window.Vue) {
    window.Vue.component('FileSelectWrapper', FileSelectWrapper)
}
// 定义一个 mixin 对象.所有 调用 FileSelect 组件,最好依赖此混入对象.

export {FileSelectWrapper}

