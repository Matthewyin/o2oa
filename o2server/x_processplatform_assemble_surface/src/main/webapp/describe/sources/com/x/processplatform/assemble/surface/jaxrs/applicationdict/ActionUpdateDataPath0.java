package com.x.processplatform.assemble.surface.jaxrs.applicationdict;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonElement;
import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.project.Applications;
import com.x.base.core.project.x_processplatform_service_processing;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.jaxrs.WoId;
import com.x.processplatform.assemble.surface.Business;
import com.x.processplatform.assemble.surface.ThisApplication;
import com.x.processplatform.core.entity.element.Application;

class ActionUpdateDataPath0 extends BaseAction {

	ActionResult<Wo> execute(String applicationDictFlag, String applicationFlag, String path0, JsonElement jsonElement)
			throws Exception {

		ActionResult<Wo> result = new ActionResult<>();
		String id = null;

		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			Business business = new Business(emc);
			Application application = business.application().pick(applicationFlag);
			if (null == application) {
				throw new ExceptionApplicationNotExist(applicationFlag);
			}
			id = business.applicationDict().getWithApplicationWithUniqueName(application.getId(), applicationDictFlag);
			if (StringUtils.isEmpty(id)) {
				throw new ExceptionApplicationDictNotExist(applicationFlag);
			}
		}
		Wo wo = ThisApplication.context().applications()
				.putQuery(x_processplatform_service_processing.class,
						Applications.joinQueryUri("applicationdict", id, path0, "data"), jsonElement, id)
				.getData(Wo.class);
		result.setData(wo);
		return result;
	}

	public static class Wo extends WoId {

	}
}