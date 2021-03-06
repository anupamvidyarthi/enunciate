/*
 * Copyright 2006-2008 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.enunciate.contract.jaxb;

import com.sun.mirror.declaration.ClassDeclaration;
import junit.framework.Test;
import net.sf.jelly.apt.decorations.declaration.PropertyDeclaration;
import net.sf.jelly.apt.freemarker.FreemarkerModel;
import org.codehaus.enunciate.apt.EnunciateFreemarkerModel;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Ryan Heaton
 */
public class TestElementRef extends TestElement {

  /**
   * tests a basic element ref.
   */
  public void testBasicElementRef() throws Exception {
    ClassDeclaration elementBeanOneDecl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.schema.ElementBeanOne");
    ComplexTypeDefinition elementBeanOneType = new ComplexTypeDefinition(elementBeanOneDecl);
    RootElementDeclaration elementBeanOne = new RootElementDeclaration(elementBeanOneDecl, elementBeanOneType);

    EnunciateFreemarkerModel model = ((EnunciateFreemarkerModel) FreemarkerModel.get());
    model.add(elementBeanOneType);
    model.add(elementBeanOne);

    ComplexTypeDefinition typeDef = new ComplexTypeDefinition((ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.schema.ElementRefBeanOne"));
    PropertyDeclaration property = findProperty(typeDef, "property1");
    ElementRef element = new ElementRef(property, typeDef);
    assertEquals(1, element.getChoices().size());
    assertSame(element, element.getChoices().iterator().next());
    assertEquals("element-bean-one", element.getName());
    assertEquals("urn:element-bean-one", element.getNamespace());
    assertNotNull(element.getRef());
    assertEquals(property.getPropertyType(), element.getAccessorType());

    assertFalse(element.isNillable());
    assertFalse(element.isRequired());
    assertEquals(0, element.getMinOccurs());
    assertEquals("1", element.getMaxOccurs());
    assertNull(element.getDefaultValue());
    assertFalse(element.isWrapped());
  }

  /**
   * tests an element ref with an explicit type.
   */
  public void testExplicitTypeElementRef() throws Exception {
    ClassDeclaration elementBeanOneDecl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.schema.ElementBeanOne");
    ComplexTypeDefinition elementBeanOneType = new ComplexTypeDefinition(elementBeanOneDecl);
    RootElementDeclaration elementBeanOne = new RootElementDeclaration(elementBeanOneDecl, elementBeanOneType);

    EnunciateFreemarkerModel model = ((EnunciateFreemarkerModel) FreemarkerModel.get());
    model.add(elementBeanOneType);
    model.add(elementBeanOne);

    ComplexTypeDefinition typeDef = new ComplexTypeDefinition((ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.schema.ElementRefBeanOne"));
    PropertyDeclaration property = findProperty(typeDef, "property2");
    ElementRef element = new ElementRef(property, typeDef);
    assertEquals(1, element.getChoices().size());
    assertSame(element, element.getChoices().iterator().next());
    assertEquals("beanThree", element.getName());
    assertNull(element.getNamespace());
    assertNotNull(element.getRef());
    assertEquals("org.codehaus.enunciate.samples.schema.BeanThree", element.getAccessorType().toString());

    assertFalse(element.isNillable());
    assertFalse(element.isRequired());
    assertEquals(0, element.getMinOccurs());
    assertEquals("1", element.getMaxOccurs());
    assertNull(element.getDefaultValue());
    assertFalse(element.isWrapped());
  }

  /**
   * tests a JAXBElement ref
   */
  public void testJAXBElementRef() throws Exception {
    ClassDeclaration elementBeanOneDecl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.schema.ElementBeanOne");
    ComplexTypeDefinition elementBeanOneType = new ComplexTypeDefinition(elementBeanOneDecl);
    RootElementDeclaration elementBeanOne = new RootElementDeclaration(elementBeanOneDecl, elementBeanOneType);

    EnunciateFreemarkerModel model = ((EnunciateFreemarkerModel) FreemarkerModel.get());
    model.add(elementBeanOneType);
    model.add(elementBeanOne);

    ComplexTypeDefinition typeDef = new ComplexTypeDefinition((ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.schema.ElementRefBeanOne"));
    PropertyDeclaration property = findProperty(typeDef, "property3");
    ElementRef element = new ElementRef(property, typeDef);
    assertEquals(1, element.getChoices().size());
    assertSame(element, element.getChoices().iterator().next());
    assertEquals("beanone", element.getName());
    assertEquals("urn:beanone", element.getNamespace());
    assertNotNull(element.getRef());
    assertEquals(property.getPropertyType(), element.getAccessorType());

    assertFalse(element.isNillable());
    assertFalse(element.isRequired());
    assertEquals(0, element.getMinOccurs());
    assertEquals("1", element.getMaxOccurs());
    assertNull(element.getDefaultValue());
    assertFalse(element.isWrapped());
  }

  /**
   * tests a basic collection element ref.
   */
  public void testBasicCollectionRef() throws Exception {
    EnunciateFreemarkerModel model = ((EnunciateFreemarkerModel) FreemarkerModel.get());

    ClassDeclaration decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOneDotOne");
    ComplexTypeDefinition type = new ComplexTypeDefinition(decl);
    RootElementDeclaration rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOneDotTwo");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanThree");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanFour");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    ComplexTypeDefinition typeDef = new ComplexTypeDefinition((ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.ElementRefsBean"));
    PropertyDeclaration property = findProperty(typeDef, "beanThrees");
    ElementRef element = new ElementRef(property, typeDef);
    assertEquals(1, element.getChoices().size());
    ElementRef firstChoice = element.getChoices().iterator().next();
    assertEquals("beanThree", firstChoice.getName());
    assertEquals("http://org.codehaus.enunciate/core/samples/another", firstChoice.getNamespace());

    assertFalse(element.isNillable());
    assertFalse(element.isRequired());
    assertEquals(0, element.getMinOccurs());
    assertEquals("unbounded", element.getMaxOccurs());
    assertNull(element.getDefaultValue());
    assertFalse(element.isWrapped());

    try {
      element.getRef();
      fail("There shouldn't have been a ref with an element with choices.");
    }
    catch (UnsupportedOperationException uoe) {

    }
  }

  /**
   * tests a collection element ref.
   */
  public void testSuperTypeCollectionRef() throws Exception {
    EnunciateFreemarkerModel model = ((EnunciateFreemarkerModel) FreemarkerModel.get());

    ClassDeclaration decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOneDotOne");
    ComplexTypeDefinition type = new ComplexTypeDefinition(decl);
    RootElementDeclaration rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOneDotTwo");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanThree");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOne");
    type = new ComplexTypeDefinition(decl);
    model.add(type);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanFour");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    ComplexTypeDefinition typeDef = new ComplexTypeDefinition((ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.ElementRefsBean"));
    PropertyDeclaration property = findProperty(typeDef, "beanOnes");
    ElementRef element = new ElementRef(property, typeDef);
    Collection<ElementRef> choices = element.getChoices();
    assertEquals(3, choices.size());
    Iterator<ElementRef> iterator = choices.iterator();
//    ElementRef firstChoice = iterator.next();
//    ElementRef secondChoice = iterator.next();
//
//    assertEquals("bean1_1", firstChoice.getName());
//    assertEquals("urn:bean1_1", firstChoice.getNamespace());
//    assertEquals("bean1_2", secondChoice.getName());
//    assertEquals("urn:bean1_2", secondChoice.getNamespace());

    assertFalse(element.isNillable());
    assertFalse(element.isRequired());
    assertEquals(0, element.getMinOccurs());
    assertEquals("unbounded", element.getMaxOccurs());
    assertNull(element.getDefaultValue());
    assertFalse(element.isWrapped());

    try {
      element.getRef();
      fail("There shouldn't have been a ref with an element with choices.");
    }
    catch (UnsupportedOperationException uoe) {

    }
  }

  /**
   * tests a collection of specified element refs.
   */
  public void testSpecifiedElementsCollectionRef() throws Exception {
    EnunciateFreemarkerModel model = ((EnunciateFreemarkerModel) FreemarkerModel.get());

    ClassDeclaration decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOneDotOne");
    ComplexTypeDefinition type = new ComplexTypeDefinition(decl);
    RootElementDeclaration rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOneDotTwo");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanThree");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanOne");
    type = new ComplexTypeDefinition(decl);
    model.add(type);

    decl = (ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.BeanFour");
    type = new ComplexTypeDefinition(decl);
    rootElementDeclaration = new RootElementDeclaration(decl, type);
    model.add(type);
    model.add(rootElementDeclaration);

    ComplexTypeDefinition typeDef = new ComplexTypeDefinition((ClassDeclaration) getDeclaration("org.codehaus.enunciate.samples.anotherschema.ElementRefsBean"));
    PropertyDeclaration property = findProperty(typeDef, "foursAndThrees");
    ElementRef element = new ElementRef(property, typeDef);
    Collection<ElementRef> choices = element.getChoices();
    assertEquals(2, choices.size());
    Iterator<ElementRef> iterator = choices.iterator();
    ElementRef firstChoice = iterator.next();
    ElementRef secondChoice = iterator.next();

    assertEquals("beanFour", firstChoice.getName());
    assertEquals("urn:BeanFour", firstChoice.getNamespace());
    assertEquals("beanThree", secondChoice.getName());
    assertEquals("http://org.codehaus.enunciate/core/samples/another", secondChoice.getNamespace());

    assertFalse(element.isNillable());
    assertFalse(element.isRequired());
    assertEquals(0, element.getMinOccurs());
    assertEquals("unbounded", element.getMaxOccurs());
    assertNull(element.getDefaultValue());
    assertFalse(element.isWrapped());

    try {
      element.getRef();
      fail("There shouldn't have been a ref with an element with choices.");
    }
    catch (UnsupportedOperationException uoe) {

    }
  }

  public static Test suite() {
    return createSuite(TestElementRef.class);
  }
}
