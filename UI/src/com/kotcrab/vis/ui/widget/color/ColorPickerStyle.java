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

package com.kotcrab.vis.ui.widget.color;

import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/** @author Kotcrab */
public class ColorPickerStyle extends WindowStyle {
	public Drawable white;
	public Drawable barSelector;
	public Drawable cross;
	public Drawable verticalSelector;
	public Drawable horizontalSelector;
	public Drawable alphaBar10px;
	public Drawable alphaBar25px;

	public ColorPickerStyle () {
	}

	public ColorPickerStyle (ColorPickerStyle other) {
		super(other);
		this.white = other.white;
		this.barSelector = other.barSelector;
		this.cross = other.cross;
		this.verticalSelector = other.verticalSelector;
		this.horizontalSelector = other.horizontalSelector;
		this.alphaBar10px = other.alphaBar10px;
		this.alphaBar25px = other.alphaBar25px;
	}
}
