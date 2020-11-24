#### Spring Framework中Bean的生命周期

#### ![lifecycle](https://github.com/SennerMing/LXRSelf_Office/blob/master/progress/images/bean_lifecycle.jpg)

结合上图分析：

​	1.首先是Bean的实例化操作；

​	2.Bean的属性注入（暂时理解为反射注入）;

​	3.BeanNameAware的setBeanNameAware()，要根据策略进行Bean在Spring IOC容器中，使用的全局唯一的Bean的名称;

​	4.BeanClassLoader的setBeanClassLoader()，Spring IOC与是对JVM控制实例的一个封装，需要设置类加载器(暂时理解为拿到Bean实例的加载环境，可以对			实例进行一些底层的操作);

​	5.BeanFactoryAware的setBeanFactory()，加载当前Bean的BeanFactory的实例，网上资料描述“ 回调提供了自己的bean实例工厂，在普通属性设置之后，在			InitializingBean.afterPropertiesSet()或者自定义初始化方法之前调用”;

​	6.EnvironmentAware的setEnvironment()，Environment中包含一些yml、properties的相关配置，归根到底就是我们常见的“PropertyResolver”;

​	7.ResourceLoaderAware的setResourceLoader()，资源加载器，对于Spring Framework来说，所有的文件都可以作为Resource来读取，getInputStrem()嘛;

​	8.ApplicationEventPublisherAware的setApplicationEventPublisher()，[参考此篇关于Spring事件机制的文章，讲的很清晰](https://blog.csdn.net/qq330983778/article/details/99762511);

​	9.MessageSourceAware的setMessageSource()，应用国际化（暂时理解为可以改一些提示信息，各种语言啊，时区什么的）;

​	10.ApplicationContextAware的setApplicationContext()，最重要的就是此操作，判断应用上下文类型，常用的就是WebApplication，WebApplication;

