/*-
 * #%L
 * StrangeFX
 * %%
 * Copyright (C) 2020 Johan Vos
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Johan Vos nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.redfx.strange.ui;

import org.redfx.strange.simulator.Model;
import org.redfx.strange.ui.render.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.layout.*;

import java.util.*;

public class QubitBoard extends Group {

    private Model model = Model.getInstance();
    private ObservableList<QubitFlow> wires = FXCollections.observableArrayList();

    private final int initialQubitNumber;

    private final VBox wiresBox = new VBox();
    private final List<Node> overlays = new LinkedList<>();

    public QubitBoard( int initialQubitNumber ) {

        this.initialQubitNumber = initialQubitNumber;
        wiresBox.getChildren().setAll(wires);

        wires.addListener( (Observable o) -> {
            wiresBox.getChildren().setAll(wires);
            model.setNQubits(wires.size());
            model.refreshRequest().set(true);
        });

        for (int i = 0; i < initialQubitNumber; i++) {
            appendQubit();
        }

        getChildren().add(wiresBox);

    }

    public void addOverlay(BoardOverlay overlay) {
        this.getChildren().add(overlay);
    }
    public ObservableList<QubitFlow> getWires() {
        return wires;
    }

    public void appendQubit() {
        wires.add( new QubitFlow(wires.size()));
    }

    public void clear() {
        wires.forEach(QubitFlow::clear);
        wires.removeIf(qb -> qb.getIndex() > (initialQubitNumber-1));
    }
}
