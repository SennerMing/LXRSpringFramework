#### PropertyEditorRegister

##### 	PropertyEditor：

​			主要的目的是为了处理Bean属性值的注入，基本类型可以直接进行处理，但如Date类型，就无法进行DI。

​			举例：通过Xml配置文件，创建一个JavaBean，xml中对该JavaBean进行配置：

```xml
<bean id="demoBean" class="springSourseAnalyes.BeanA" scope="singleton" init-method="init" lazy-init="true" >
		<property name="date" value="2018-08-22" />
		<property name="nameString" value="崔春驰" />
		<!-- <property name="b" ref="b" /> -->
</bean>
```

​			这样JavaBean中的类型为Java.util.Date类型的属性date就会报错，PropertyEditor的作用就在于此，将Xml或者其他值，处理DI	进入JavaBean中。

​	针对以上问题的解决方案有：

​	1.通过继承PropertyEditorSupport的方式

```java
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;
 
/**
 * 自定义属性编辑器   因为日期date型的注入会出现问题，没法转型
 * 重新复写setAsText方法
 * 
 * 当注入的时候遇到Date时，就会使用自定义的属性编辑器
 * 
 * 两种方式：1：继承PropertyEditorSupport类
 * 		   2：实现PropertyEditorRegistrar接口
 * 
 * 在xml配置文件配置CustomEditorConfigurer的class类，其实现了BeanFactoryPostProcessor
 * <bean class="org.springframework.beans.factory.config.CustomEditorConfigurer">
		<property name="customEditors">
			<map>
				<entry key="java.util.Date" >
					<bean class="springSourseAnalyes.DatePropertyEditor" >
						<property name="format" value="yyyy-MM-dd" />
					</bean>
				</entry>
			</map>
		</property>
	</bean>
 * @author MTW
 *
 */
public class DatePropertyEditor extends PropertyEditorSupport {
	
	private String format = "yyyy-MM-dd";
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		System.out.println("属性："+text);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		try {
			Date date = dateFormat.parse(text);
			this.setValue(date);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage()+"抛出异常。。。。。");
		}
	}
 
}
```

​	2.通过实现PropertyEditorRegister的方式

```java
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
 
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;
 
/**
 * 自定义属性编辑器   因为日期date型的注入会出现问题，没法转型
 * 重新复写setAsText方法
 * 当注入的时候遇到Date时，就会使用自定义的属性编辑器
 * 实现PropertyEditorRegistrar接口
 * 
 * 使用常用的属性编辑器CustomDateEditor
	<bean class="org.springframework.beans.factory.config.CustomEditorConfigurer">
		<property name="propertyEditorRegistrars">
			<list>
				<bean class="springSourseAnalyes.DatePropertyEditorRegistrar" />
			</list>
		</property>
	</bean>
 * @author MTW
 *
 */
public class DatePropertyEditorRegistrar implements PropertyEditorRegistrar{
	
	/**
	 * CustomDateEditor 其实就是继承PropertyEditorSupport
	 */
	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
```

​	以上子类与实现类都要将Bean归Spring管理；

在配置文件引入类型为org.springframework.beans.factory.config.CustomEditorConfigurer的bean，并在属性customEditors中加入自定义的属性编辑器，其中key为属性编辑器所对应的类型。通过这样的配置，当Spring在注入bean的属性时一旦遇到 了java.util.Date类型的属性会自动调用自定义的DatePropertyEditor解析器进行解析，并用解析结果代替配置属性进行注入。

[详情参考，PropertyEditor相关讲的很详细](https://blog.csdn.net/cuichunchi/article/details/90407632)



#### 



#### AbstractApplicationContext

##### 	refresh()中的函数含义：

​	prepareBeanFactory()→beanFactory.ignoreDependencyInterface()相关，忽略该接口的实现类中和接口setter方法入参类型相同的依赖。[参考文章](https://www.jianshu.com/p/3c7e0608ff1f?from=singlemessage)

​	prepareBeanFactory()→beanFactory.registerResolvableDependency()相关，此函数主要解决当有一个接口有多个实现类，默认注入哪个实现类。[参考文章](https://blog.csdn.net/yuge1123/article/details/106053857)

​	LoadTimeWeaver,在Java语言中，从织入切面的方式来看，存在三种织入方式：编译期织入、类加载期织入和运行期织入。编译期织入是指在Java编译期，采用特殊的编译器，将切面织入到Java类中；而类加载期织入则指通过特殊的类加载器，在类字节码加载到JVM时，织入切面；运行期织入则是采用CGLib工具或JDK动态代理进行切面的织入。LoadTimeWeaver就是类加载期织入。[参考文章1](https://www.cnblogs.com/wade-luffy/p/6073702.html)

[参考文章2](https://www.cnblogs.com/takumicx/p/10150344.html)

CopyOnWriteArrayList理解：[参考文章](https://www.jianshu.com/p/9b6a4d0b94ac)

registerSingleton()函数中，有一个方法调用：

```java
private void updateManualSingletonNames(Consumer<Set<String>> action, Predicate<Set<String>> condition)
```

其中对Consumer与Predicate的理解，请点击[参考文章](https://www.cnblogs.com/SIHAIloveYAN/p/11288064.html)
BeanFactoryPostProcessor与BeanPostProcessor，[参考文章](https://blog.csdn.net/caihaijiang/article/details/35552859)



#### BeanDefinition

​	该类的类注释中提到了”introspect“，计算机英语为”内省“的意思，Java的内省机制可以查看该[参考文章](https://www.cnblogs.com/alice-cj/p/11444723.html)，

内省机制，暂时理解为底层利用反射，对Bean的属性进行动态的感知与修改。

​	BeanDefinition的注释中提到，该类与BeanFactoryPostProcessor为组合关系，使用到BeanFactoryPostProcessor实现对Spring Bean的处理（包括introspect，modify property values，modify bean metadata），该类扩展了AttributeAccessor与BeanMetadataElement接口，分别对应属性信息的获取（设置属性值，获得属性值，删除属性，判断有无某属性，所有属性名称）与Bean的元数据信息（获得元信息组件的配置资源[暂不理解]）











​			

​		