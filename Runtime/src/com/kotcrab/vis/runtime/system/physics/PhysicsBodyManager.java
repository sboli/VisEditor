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

package com.kotcrab.vis.runtime.system.physics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Manager;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kotcrab.vis.runtime.RuntimeConfiguration;
import com.kotcrab.vis.runtime.component.*;

/** @author Kotcrab */
@Wire
public class PhysicsBodyManager extends Manager {
	private PhysicsSystem physicsSystem;

	private ComponentMapper<PhysicsPropertiesComponent> physicsPropCm;
	private ComponentMapper<PhysicsComponent> physicsCm;
	private ComponentMapper<PolygonComponent> polygonCm;
	private ComponentMapper<SpriteComponent> spriteCm;
	private ComponentMapper<PhysicsSpriteComponent> physicsSpriteCm;

	private World world;
	private RuntimeConfiguration runtimeConfig;

	public PhysicsBodyManager (RuntimeConfiguration runtimeConfig) {
		this.runtimeConfig = runtimeConfig;
	}

	@Override
	protected void initialize () {
		world = physicsSystem.getPhysicsWorld();
	}

	@Override
	public void added (int entityId) {
		Entity entity = super.world.getEntity(entityId);
		if (physicsPropCm.has(entityId) == false || polygonCm.has(entityId) == false || spriteCm.has(entityId) == false)
			return;

		PhysicsPropertiesComponent physicsProperties = physicsPropCm.get(entityId);
		PolygonComponent polygon = polygonCm.get(entityId);
		SpriteComponent sprite = spriteCm.get(entityId);

		if (physicsProperties.adjustOrigin) sprite.setOrigin(0, 0);

		Vector2 worldPos = new Vector2(sprite.getX(), sprite.getY());

		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(worldPos);

		Body body = world.createBody(bodyDef);
		body.setType(physicsProperties.bodyType);
		body.setUserData(entity);

		body.setGravityScale(physicsProperties.gravityScale);
		body.setLinearDamping(physicsProperties.linearDamping);
		body.setAngularDamping(physicsProperties.angularDamping);

		body.setBullet(physicsProperties.bullet);
		body.setFixedRotation(physicsProperties.fixedRotation);
		body.setSleepingAllowed(physicsProperties.sleepingAllowed);
		body.setActive(physicsProperties.active);

		for (Vector2[] vs : polygon.faces) {
			for (Vector2 v : vs) { //polygon component stores data in world cords, we need to convert it to local cords
				v.sub(worldPos);
			}

			PolygonShape shape = new PolygonShape();
			shape.set(vs);

			FixtureDef fd = new FixtureDef();
			fd.density = physicsProperties.density;
			fd.friction = physicsProperties.friction;
			fd.restitution = physicsProperties.restitution;
			fd.isSensor = physicsProperties.sensor;
			fd.shape = shape;
			fd.filter.maskBits = physicsProperties.maskBits;
			fd.filter.categoryBits = physicsProperties.categoryBits;

			body.createFixture(fd);
			shape.dispose();
		}

		entity.edit()
				.add(new PhysicsComponent(body))
				.add(new PhysicsSpriteComponent(sprite.getRotation()));
	}

	@Override
	public void deleted (int entityId) {
		if (runtimeConfig.autoDisposeBox2dBodyOnEntityRemove == false || physicsCm.has(entityId) == false) return;
		PhysicsComponent physics = physicsCm.get(entityId);
		if (physics.body == null) return;
		world.destroyBody(physics.body);
		physics.body = null;
	}
}
