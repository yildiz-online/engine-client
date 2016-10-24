//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.client.mission.task;

import be.yildiz.shared.mission.task.Task;
import be.yildiz.shared.mission.task.TaskId;
import be.yildiz.shared.mission.task.TaskStatusListener;

/**
 * @author Grégory Van den Borre
 */
public class ClientTask implements Task{

    private final Task task;

    private final ClientTaskGuiMaterialization materialization;

    public ClientTask(Task task, ClientTaskGuiMaterialization mat) {
        this.task = task;
        this.materialization = mat;
    }

    @Override
    public void addListener(TaskStatusListener taskStatusListener) {
        this.task.addListener(taskStatusListener);
    }

    @Override
    public TaskId getId() {
        return this.task.getId();
    }

    @Override
    public boolean isCompleted() {
        return this.task.isCompleted();
    }

    @Override
    public boolean isFailed() {
        return this.task.isFailed();
    }
}
