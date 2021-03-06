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

package org.codehaus.enunciate.template.freemarker;

import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.TypeParameterDeclaration;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Iterator;
import java.util.List;

import org.codehaus.enunciate.ClientName;

/**
 * Gets the client-side component type for the specified classname.
 *
 * @author Ryan Heaton
 */
public class SimpleNameWithParamsMethod implements TemplateMethodModelEx {

  private final ClassnameForMethod typeConversion;

  public SimpleNameWithParamsMethod(ClassnameForMethod typeConversion) {
    this.typeConversion = typeConversion;
  }

  /**
   * Gets the client-side package for the type, type declaration, package, or their string values.
   *
   * @param list The arguments.
   * @return The string value of the client-side package.
   */
  public Object exec(List list) throws TemplateModelException {
    if (list.size() < 1) {
      throw new TemplateModelException("The convertPackage method must have the class or package as a parameter.");
    }

    TemplateModel from = (TemplateModel) list.get(0);
    Object unwrapped = BeansWrapper.getDefaultInstance().unwrap(from);
    if (!(unwrapped instanceof TypeDeclaration)) {
      throw new TemplateModelException("A type declaration must be provided.");
    }

    boolean noParams = list.size() > 1 && Boolean.FALSE.equals(BeansWrapper.getDefaultInstance().unwrap((TemplateModel) list.get(1)));
    TypeDeclaration declaration = (TypeDeclaration) unwrapped;
    String simpleNameWithParams = this.typeConversion.isUseClientNameConversions() && declaration.getAnnotation(ClientName.class) != null ? declaration.getAnnotation(ClientName.class).value() : declaration.getSimpleName();
    if (!noParams && this.typeConversion.isJdk15() && declaration.getFormalTypeParameters() != null && !declaration.getFormalTypeParameters().isEmpty()) {
      simpleNameWithParams += "<";
      Iterator<TypeParameterDeclaration> paramIt = declaration.getFormalTypeParameters().iterator();
      while (paramIt.hasNext()) {
        simpleNameWithParams += this.typeConversion.convert(paramIt.next());
        if (paramIt.hasNext()) {
          simpleNameWithParams += ", ";
        }
      }
      simpleNameWithParams += ">";
    }
    return simpleNameWithParams;
  }

}