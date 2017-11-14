/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.client.mission;

import be.yildiz.common.id.PlayerId;
import be.yildiz.common.translation.Key;
import be.yildiz.module.graphic.Material;
import be.yildiz.shared.mission.Mission;
import be.yildiz.shared.mission.MissionId;
import be.yildizgames.engine.feature.mission.reward.RewardId;
import be.yildizgames.engine.feature.mission.task.TaskId;

import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
public class ClientMission implements Mission{

    private final Mission mission;

    private final ClientMissionGuiMaterialization materialization;

    public ClientMission(Mission mission, ClientMissionGuiMaterialization materialization) {
        this.mission = mission;
        this.materialization = materialization;
    }

    @Override
    public final boolean canStartFor(PlayerId p) {
        return this.mission.canStartFor(p);
    }

    @Override
    public final Set<TaskId> getTasks() {
        return this.mission.getTasks();
    }

    @Override
    public final boolean hasTask(TaskId task) {
        return this.mission.hasTask(task);
    }

    @Override
    public final MissionId getId() {
        return this.mission.getId();
    }

    @Override
    public final RewardId getReward() {
        return this.mission.getReward();
    }

    public final Key getTitle() {
        return this.materialization.title;
    }

    public final Key getDescription() {
        return this.materialization.description;
    }

    public final Material getIcon() {
        return this.materialization.image;
    }
}
