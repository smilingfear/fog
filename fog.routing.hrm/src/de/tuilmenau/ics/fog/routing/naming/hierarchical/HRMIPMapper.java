/*******************************************************************************
 * Forwarding on Gates Simulator/Emulator - Hierarchical Routing Management
 * Copyright (c) 2012, Integrated Communication Systems Group, TU Ilmenau.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 ******************************************************************************/
package de.tuilmenau.ics.fog.routing.naming.hierarchical;

import java.util.HashMap;
import java.util.LinkedList;

import de.tuilmenau.ics.fog.facade.Name;
import de.tuilmenau.ics.fog.routing.hierarchical.clusters.Cluster;
import de.tuilmenau.ics.fog.ui.Logging;


public class HRMIPMapper
{
	private HashMap<Cluster, LinkedList<Name>> mClusterToIPMapper = new HashMap<Cluster, LinkedList<Name>>();
	private HashMap<HRMID, Cluster> mHRMIDToCluster = new HashMap<HRMID, Cluster>();
	private static HRMIPMapper sHRMIPMapperSingleton = null;
	private static LinkedList<HRMID> mHRMIDs = new LinkedList<HRMID>();
	
	public static HRMIPMapper getHRMIPMapper()
	{
		if(sHRMIPMapperSingleton == null) {
			sHRMIPMapperSingleton = new HRMIPMapper();
		}
		return sHRMIPMapperSingleton;
	}
	
	public void registerIPAddressForCluster(Cluster pCluster, Name pIP)
	{
		if(mClusterToIPMapper.get(pCluster) == null) {
			mClusterToIPMapper.put(pCluster, new LinkedList<Name>());
		}
		if(!mClusterToIPMapper.get(pCluster).contains(pIP)) {
			mClusterToIPMapper.get(pCluster).add(pIP);
		}
		for(Cluster tCluster : mClusterToIPMapper.keySet()) {
			Logging.log(this, tCluster + " consists of " + mClusterToIPMapper.get(tCluster));
		}
	}
	
	public LinkedList<Name> getIPFromHRMID(HRMID pHRMID)
	{
		Logging.log(this, "Requesting IP from HRMID:\n " +pHRMID + ":\n " + mHRMIDToCluster.get(pHRMID) + "\n" + mClusterToIPMapper.get(mHRMIDToCluster.get(pHRMID)));
		if(mClusterToIPMapper.get(mHRMIDToCluster.get(pHRMID)) == null) {
			Cluster tCluster = mHRMIDToCluster.get(pHRMID);
			for(Cluster tCandidate : mClusterToIPMapper.keySet()) {
				if(tCandidate.equals(tCluster)) {
					
				}
			}
		}
		return mClusterToIPMapper.get(mHRMIDToCluster.get(pHRMID));
	}
	
	public void registerHRMIDForCluster(Cluster pCluster, HRMID pHRMID)
	{
		mHRMIDToCluster.put(pHRMID, pCluster);
		for(HRMID tValue: mHRMIDToCluster.keySet()) {
			Logging.log(this, tValue + " consists of " + mHRMIDToCluster.get(tValue));
		}
	}
	
	public String toString()
	{
		return this.getClass().getSimpleName();
	}
	
	public static void registerHRMID(HRMID pHRMID)
	{
		mHRMIDs.add(pHRMID);
		Logging.log("Registered " + pHRMID + " for region limitation");
	}
	
	public static LinkedList<HRMID> getHRMIDs()
	{
		return mHRMIDs;
	}
}
