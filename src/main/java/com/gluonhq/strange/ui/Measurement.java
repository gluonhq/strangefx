/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2017, Gluon Software
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gluonhq.strange.ui;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class Measurement extends Region {

    private Label label = new Label();
    private Pane  progress = new Pane();

    public Measurement() {

        getStyleClass().add("measurement");

        initUI();

        updateMeasuredChance();
        measuredChanceProperty.addListener( (Observable o) -> updateMeasuredChance());

    }

    private void initUI() {

        BorderPane progressBase = new BorderPane();
        progressBase.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        progressBase.setBottom(progress);
        progress.setPrefHeight(0);
        progress.getStyleClass().add("progress");

        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        StackPane stack = new StackPane(progressBase, label);
        stack.prefWidthProperty().bind(widthProperty());
        stack.prefHeightProperty().bind(heightProperty());

        getChildren().add(stack);

        prefWidthProperty().bind(heightProperty());
    }


    private void updateMeasuredChance() {
        label.setText( measuredChanceAsString());
        progress.setPrefHeight( getHeight() * getMeasuredChance() );
    }

    private String measuredChanceAsString() {
        double chance = getMeasuredChance();
        if ( chance == 0d) return "Off";
        if (chance == 1d) return "On";
        return String.format("%2.1f%%", chance * 100);
    }

    // measuredChanceProperty
    private final DoubleProperty measuredChanceProperty = new SimpleDoubleProperty(this, "measured chance", 0) {
        @Override
        public void set(double newValue) {
            System.out.println("mcp set to "+newValue);
            if (newValue >= 0 && newValue <= 1) {
                super.set(newValue);
            }
        }
    };

    public final DoubleProperty measuredChanceProperty() {
        return measuredChanceProperty;
    }

    public final double getMeasuredChance() {
        return measuredChanceProperty.get();
    }

    public final void setMeasuredChance(double value) {
        measuredChanceProperty.set(value);
    }


}
