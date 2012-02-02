package output;

import java.beans.BeanInfo;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Expression;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.Statement;
import java.lang.reflect.Method;

public class MeasurementPersistenceDelegate extends DefaultPersistenceDelegate {

	protected Expression instantiate(Object old, Encoder out) {
		return new Expression(old, old.getClass(), "getInstance", null);
	}

	protected void initialize(@SuppressWarnings("rawtypes") Class type, Object oldI, Object newI, Encoder out) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] descriptors = beanInfo
					.getPropertyDescriptors();
			for (int i = 0; i < descriptors.length; i++) {
				Method readMethod = descriptors[i].getReadMethod();
				Method writeMethod = descriptors[i].getWriteMethod();
				if (writeMethod != null && readMethod != null) {
					out.writeStatement(new Statement(newI, writeMethod
							.getName(), new Object[] { readMethod.invoke(oldI,
									new Object[] {}) }));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
