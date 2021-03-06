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

package com.kotcrab.vis.editor.scene;

import com.kotcrab.vis.runtime.scene.LayerCordsSystem;
import com.kotcrab.vis.runtime.util.annotation.VisTag;

/**
 * EditorScene layer class
 * @author Kotcrab
 */
public class Layer {
	@VisTag(0) public String name;
	@VisTag(1) public int id;
	@VisTag(2) public boolean locked = false;
	@VisTag(3) public boolean visible = true;
	@VisTag(4) public LayerCordsSystem cordsSystem = LayerCordsSystem.WORLD;

	public Layer (String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String toString () {
		return name;
	}
}
