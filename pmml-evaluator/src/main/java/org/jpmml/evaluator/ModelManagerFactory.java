/*
 * Copyright (c) 2010 University of Tartu
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jpmml.evaluator;

import java.io.Serializable;
import java.util.List;

import org.dmg.pmml.Model;
import org.dmg.pmml.PMML;

abstract
public class ModelManagerFactory implements Serializable {

	protected ModelManagerFactory(){
	}

	abstract
	public ModelManager<? extends Model> getModelManager(PMML pmml, Model model);

	public ModelManager<? extends Model> getModelManager(PMML pmml){
		return getModelManager(pmml, (String)null);
	}

	/**
	 * @param modelName The name of the Model to be selected. If <code>null</code>, then the default model is selected.
	 *
	 * @throws IllegalArgumentException If the model cannot be selected.
	 *
	 * @see Model#getModelName()
	 */
	public ModelManager<? extends Model> getModelManager(PMML pmml, String modelName){

		if(!pmml.hasModels()){
			throw new InvalidFeatureException(pmml);
		}

		List<Model> models = pmml.getModels();
		for(Model model : models){

			if(modelName == null || (modelName).equals(model.getModelName())){
				return getModelManager(pmml, model);
			}
		}

		throw new IllegalArgumentException(modelName);
	}
}