// package com.example.kdemo.web
//
// import com.shadow.BootConfig
// import com.shadow.ns.service.core.NsSecurityService
// import org.apache.commons.lang3.SystemUtils
// import org.springframework.boot.web.servlet.FilterRegistrationBean
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration
// import org.springframework.http.CacheControl
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry
// import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
// import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
// import org.springframework.web.servlet.mvc.WebContentInterceptor
//
// @Configuration
// class WebConfig : WebMvcConfigurer {
//
//     override fun addInterceptors(registry: InterceptorRegistry) {
//         val wic = WebContentInterceptor()
//         wic.cacheControl = CacheControl.noCache()
//         wic.cacheSeconds = 0
//         registry.addInterceptor(wic)
//
//         registry.addInterceptor(SecuredHandlerInterceptor())
//     }
//
//     override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
//         val uiDir = BootConfig.uiDir
//         if (uiDir != null) {
//             // 必须有最后的斜线
//             val path = if (SystemUtils.IS_OS_WINDOWS) {
//                 "file:///${uiDir.absolutePath}/"
//             } else {
//                 "file:${uiDir.absolutePath}/"
//             }
//             // registry.addResourceHandler("/**/*.html").addResourceLocations(FileSystemResource(uiDir))
//             // registry.addResourceHandler("/static/**").addResourceLocations(FileSystemResource(uiDir))
//             registry.addResourceHandler("/*.html").addResourceLocations(path)
//             registry.addResourceHandler("/static/**").addResourceLocations(path)
//         }
//     }
//
//     override fun addViewControllers(registry: ViewControllerRegistry) {
//         registry.addViewController("/").setViewName("redirect:/index.html")
//     }
//
//     // @Bean
//     // fun rewriteFilterBean(): FilterRegistrationBean<*> {
//     //     val registrationBean: FilterRegistrationBean<RewriteFilter> = FilterRegistrationBean<RewriteFilter>()
//     //     val rewriteFilter = RewriteFilter()
//     //     registrationBean.filter = rewriteFilter
//     //     registrationBean.order = 2
//     //     return registrationBean
//     // }
//
//     @Bean
//     fun securityFilterBean(securityService: NsSecurityService): FilterRegistrationBean<*> {
//         val registrationBean: FilterRegistrationBean<SecurityFilter> = FilterRegistrationBean<SecurityFilter>()
//         val securityFilter = SecurityFilter(securityService)
//         registrationBean.filter = securityFilter
//         registrationBean.order = 1
//         return registrationBean
//     }
//
// }