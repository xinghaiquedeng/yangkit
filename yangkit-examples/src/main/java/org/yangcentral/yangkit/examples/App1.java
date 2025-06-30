/*
 * Copyright 2023 INSA Lyon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yangcentral.yangkit.examples;

import org.yangcentral.yangkit.base.YangElement;
import org.yangcentral.yangkit.common.api.validate.ValidatorResult;
import org.yangcentral.yangkit.model.api.schema.YangSchemaContext;
import org.yangcentral.yangkit.model.api.stmt.Module;
import org.yangcentral.yangkit.model.api.stmt.YangStatement;
import org.yangcentral.yangkit.parser.YangParser;
import org.yangcentral.yangkit.parser.YangParserEnv;
import org.yangcentral.yangkit.parser.YangParserException;
import org.yangcentral.yangkit.register.YangStatementImplRegister;
import org.yangcentral.yangkit.register.YangStatementRegister;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * Schema provider at Kafka usecase: validate from yangString
 *
 */
public class App1 {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = App1.class.getClassLoader().getResourceAsStream("App1/insa-test.yang");
        assert inputStream != null;
        String yang = new String(IOUtils.toByteArray(inputStream));
        inputStream.close();
        System.out.println(yang);

        YangStatementImplRegister.registerImpl();
        YangParser yangParser = new YangParser();
        YangParserEnv yangParserEnv = new YangParserEnv();
        yangParserEnv.setYangStr(yang);
        yangParserEnv.setFilename("insa-test");
        yangParserEnv.setCurPos(0);
        List<YangElement> yangElements;
        try {
            yangElements = yangParser.parseYang(yang, yangParserEnv);
        } catch (YangParserException e) {
            System.out.println(e.getDescription());
            throw new RuntimeException(e);
        }

        System.out.println("How many modules? " + yangElements.size());
        YangSchemaContext context = YangStatementRegister.getInstance().getSchemeContextInstance();
        // Add the yang module to the context;
        for (YangElement element : yangElements) {
            if (element instanceof YangStatement) {
                context.addModule((Module) element);
            }
        }
        // get name of root module
        String moduleName = yangParserEnv.getFilename();
        if (context.getModules().size() > 0) {
            moduleName = context.getModules().get(0).getModuleId().getModuleName();
        }
        // Add parsed module to context
        context.getParseResult().put(moduleName, yangElements);
        for (YangElement el : yangElements) {
            System.out.println(el);
        }
        ValidatorResult validatorResult = context.validate();
        System.out.println("Is valid? " + validatorResult.isOk());
    }
}
