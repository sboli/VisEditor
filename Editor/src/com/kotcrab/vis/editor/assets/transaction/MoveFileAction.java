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

package com.kotcrab.vis.editor.assets.transaction;

import com.badlogic.gdx.files.FileHandle;
import com.kotcrab.vis.editor.module.scene.UndoableAction;

public class MoveFileAction implements UndoableAction {
	private final FileHandle source;
	private final FileHandle target;

	public MoveFileAction (FileHandle source, FileHandle target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public void execute () {
		source.moveTo(target);
	}

	@Override
	public void undo () {
		target.moveTo(source);
	}
}
