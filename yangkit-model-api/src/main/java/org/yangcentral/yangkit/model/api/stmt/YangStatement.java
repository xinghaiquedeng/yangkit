package org.yangcentral.yangkit.model.api.stmt;

import org.yangcentral.yangkit.base.BuildPhase;
import org.yangcentral.yangkit.base.YangContext;
import org.yangcentral.yangkit.base.YangElement;
import org.yangcentral.yangkit.common.api.QName;
import org.yangcentral.yangkit.common.api.validate.ValidatorResult;
import java.util.List;

public interface YangStatement extends YangElement {
   QName getYangKeyword();

   String getArgStr();

   void setArgStr(String argStr);

   List<YangElement> getSubElements();

   List<YangStatement> getSubStatement(QName keyword);

   List<YangUnknown> getUnknowns();

   ValidatorResult build(BuildPhase buildPhase);

   ValidatorResult build();

   ValidatorResult validate();

   ValidatorResult getValidateResult();

   void setValidateResult(ValidatorResult validatorResult);

   boolean isBuilt();

   boolean isBuilding();

   boolean isValidated();

   boolean addChild(YangElement yangElement);

   boolean addChild(int index, YangElement yangElement);

   boolean updateChild(YangStatement yangStatement);

   boolean updateChild(int index, YangElement yangElement);

   boolean removeChild(YangElement yangElement);

   void setChildren(List<YangElement> yangElements);

   YangStatement getParentStatement();

   void setParentStatement(YangStatement parentStatement);

   YangContext getContext();

   void setContext(YangContext context);

   ValidatorResult init();

   boolean isInit();

   <T extends YangStatement> T getSelf();

   YangStatement clone();

   YangStatement clonedBy();

   boolean isErrorStatement();

   void setErrorStatement(boolean errorStatement);

   ValidatorResult afterValidate();

   List<YangStatement> getEffectiveSubStatements();
}
