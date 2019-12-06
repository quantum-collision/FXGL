/*
 * FXGL - JavaFX Game Library. The MIT License (MIT).
 * Copyright (c) AlmasB (almaslvl@gmail.com).
 * See LICENSE for details.
 */

package com.almasb.fxgl.dsl.components

import com.almasb.fxgl.entity.component.Component
import javafx.geometry.Point2D

/**
 * Generic projectile component.
 * Automatically rotates the entity based on velocity direction.
 * The rotation of 0 degrees is assumed to be facing right.
 *
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
class ProjectileComponent(direction: Point2D, speed: Double) : Component() {

    var velocity: Point2D = direction.normalize().multiply(speed)
        private set

    /**
     * Set direction in which projectile is moving.
     */
    var direction: Point2D
        get() = velocity.normalize()
        set(direction) {
            velocity = direction.normalize().multiply(speed)
            if (rotation)
                entity.rotateToVector(velocity)
        }

    var speed: Double = speed
        set(value) {
            field = value

            velocity = velocity.normalize().multiply(speed)
            if (rotation)
                getEntity().rotateToVector(velocity)
        }

    private var rotation: Boolean = true
    /**
     * Allow to disable / enable projectile rotation towards direction of travel.
     */
    fun allowRotation(allowRotation: Boolean): ProjectileComponent {
        rotation = allowRotation
        return this
    }

    override fun onAdded() {
        if (rotation)
            entity.rotateToVector(velocity)
    }

    override fun onUpdate(tpf: Double) {
        entity.translate(velocity.multiply(tpf))
    }
}
