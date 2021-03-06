/*
 * Copyright 2014-2015 See AUTHORS file.
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

package com.kotcrab.vis.editor.ui.scene.entityproperties.components;

import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.editor.entity.SpriterPropertiesComponent;
import com.kotcrab.vis.editor.module.ModuleInjector;
import com.kotcrab.vis.editor.proxy.EntityProxy;
import com.kotcrab.vis.editor.ui.scene.entityproperties.autotable.AutoComponentTable;
import com.kotcrab.vis.editor.util.vis.EntityUtils;
import com.kotcrab.vis.runtime.component.SpriterComponent;
import com.kotcrab.vis.runtime.spriter.Entity;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisSelectBox;
import com.kotcrab.vis.ui.widget.VisTable;

/** @author Kotcrab */
public class SpriterPropertiesComponentTable extends AutoComponentTable<SpriterPropertiesComponent> {

	private VisSelectBox<String> animSelectBox;

	public SpriterPropertiesComponentTable (ModuleInjector injector) {
		super(injector, SpriterPropertiesComponent.class, false);
	}

	@Override
	protected void init () {
		super.init();

		animSelectBox = new VisSelectBox<>();
		animSelectBox.getSelection().setProgrammaticChangeEvents(false);
		animSelectBox.addListener(properties.getSharedSelectBoxChangeListener());

		VisTable table = new VisTable(true);
		table.add(new VisLabel("Animation"));
		table.add(animSelectBox);

		add(table);
	}

	@Override
	public void updateUIValues () {
		super.updateUIValues();

		Array<EntityProxy> proxies = properties.getProxies();
		if (EntityUtils.isMultipleEntitiesSelected(proxies) == false) {
			SpriterComponent spriter = EntityUtils.getFirstEntity(proxies).getComponent(SpriterComponent.class);
			animSelectBox.setDisabled(false);

			Entity entity = spriter.player.getEntity();

			Array<String> animations = new Array<>(entity.animations());

			for (int i = 0; i < entity.animations(); i++) {
				animations.add(entity.getAnimation(i).name);
			}

			animSelectBox.setItems(animations);
		} else {
			animSelectBox.setDisabled(true);
			animSelectBox.setItems("<select one entity>");
		}
	}

	@Override
	public void setValuesToEntities () {
		super.setValuesToEntities();

		Array<EntityProxy> proxies = properties.getProxies();
		if (EntityUtils.isMultipleEntitiesSelected(proxies) == false) {
			SpriterComponent spriter = EntityUtils.getFirstEntityComponent(proxies, SpriterComponent.class);
			SpriterPropertiesComponent properties = EntityUtils.getFirstEntityComponent(proxies, SpriterPropertiesComponent.class);
			Entity entity = spriter.player.getEntity();

			properties.animation = entity.getAnimation(animSelectBox.getSelected()).id;
		}

		EntityUtils.stream(proxies, SpriterComponent.class, (entity, spriterComponent) -> {
			SpriterPropertiesComponent propertiesComponent = entity.getComponent(SpriterPropertiesComponent.class);
			spriterComponent.player.setScale(propertiesComponent.scale);
			spriterComponent.playOnStart = propertiesComponent.playOnStart;
			spriterComponent.defaultAnimation = propertiesComponent.animation;
			spriterComponent.player.setAnimation(propertiesComponent.animation);
			spriterComponent.animationPlaying = propertiesComponent.previewInEditor;
		});
	}
}
