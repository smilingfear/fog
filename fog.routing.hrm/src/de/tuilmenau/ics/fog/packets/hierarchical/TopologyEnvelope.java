/*******************************************************************************
 * Forwarding on Gates Simulator/Emulator - Hierarchical Routing Management
 * Copyright (c) 2012, Integrated Communication Systems Group, TU Ilmenau.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 ******************************************************************************/
package de.tuilmenau.ics.fog.packets.hierarchical;

import java.io.Serializable;
import java.util.LinkedList;

import de.tuilmenau.ics.fog.facade.Name;
import de.tuilmenau.ics.fog.routing.hierarchical.HierarchicalSignature;
import de.tuilmenau.ics.fog.routing.hierarchical.RoutingServiceLinkVector;
import de.tuilmenau.ics.fog.routing.hierarchical.clusters.ClusterDummy;
import de.tuilmenau.ics.fog.routing.naming.hierarchical.HRMID;
import de.tuilmenau.ics.fog.routing.naming.hierarchical.HRMName;

/**
 * 
 * This object is used to distribute the Forwarding Information Base to the members of the cluster
 */
public class TopologyEnvelope implements Serializable
{
	private static final long serialVersionUID = 8442835110014485795L;
	private LinkedList<Name> mIgnoreList = null;
	private HRMID mHRMID = null;
	private LinkedList<FIBEntry> mForwardingEntries;
	private LinkedList<FIBEntry> mPushThrougs = null;
	private LinkedList<HierarchicalSignature> mApprovedSignatures = new LinkedList<HierarchicalSignature>();
	
	/**
	 * 
	 * @param pHRMID constructor must contain an HRMID
	 */
	public TopologyEnvelope(HRMID pHRMID)
	{
		mHRMID = pHRMID;
		mForwardingEntries = new LinkedList<FIBEntry>();
	}
	
	public TopologyEnvelope()
	{
	}
	
	/**
	 * 
	 * @param pSignature is used to sign FIBEntries so the entity may decide whether
	 *  (1) the coordinator is reliable for that route
	 *  (2) FIBEntries could be signed
	 */
	public void addApprovedSignature(HierarchicalSignature pSignature)
	{
		if(!mApprovedSignatures.contains(pSignature)) {
			mApprovedSignatures.add(pSignature);
		}
	}
	
	/**
	 * 
	 * @return list of approved signatures for this TopologyEnvelope
	 */
	public LinkedList<HierarchicalSignature> getApprovedSignatures()
	{
		return mApprovedSignatures;
	}
	
	/**
	 * 
	 * @param pHRMID is the HRMID the receiver of this packet has to choose
	 */
	public void setHRMID(HRMID pHRMID)
	{
		mHRMID = pHRMID;
	}
	
	/**
	 * 
	 * @param pName entry that is supposed to be ignored on address distribution on cluster below in case two virtual nodes are neighbors 
	 */
	public void addIgnoreEntry(Name pName)
	{
		if(mIgnoreList == null) {
			mIgnoreList = new LinkedList<Name>();
		}
		if(!mIgnoreList.contains(pName)) {
			mIgnoreList.add(pName);
		}
	}
	
	/**
	 * 
	 * @return List of entries that should be skippied during address distribution
	 */
	public LinkedList<Name> getIgnoreEntries()
	{
		return mIgnoreList;
	}
	
	/**
	 * 
	 * @return HRMID of the receiver of this packet
	 */
	public HRMID getHRMID()
	{
		return mHRMID;
	}
	
	/**
	 * 
	 * @return List of entries that contian information on how to reach a given HRMID
	 */
	public LinkedList<FIBEntry> getEntries()
	{
		return mForwardingEntries;
	}
	
	/**
	 * @deprecated This is an old way of pushing paths from one node to another one
	 * @return list of FIBEntries that are use for long node-to-coordinator communication - those FIBEntries contained long paths
	 */
	public LinkedList<FIBEntry> getPushThrougs()
	{
		return mPushThrougs;
	}
	/**
	 * @deprecated This is an old way of pushing paths from one node to another one
	 * @param pEntry is a FIBEntry that is used for long node-to-coordinator communication - those FIBEntries contained long paths
	 */
	public void addPushThrough(FIBEntry pEntry)
	{
		if(mPushThrougs == null) {
			mPushThrougs = new LinkedList<FIBEntry>();
		}
		mPushThrougs.add(pEntry);
	}
	
	/**
	 * 
	 * @param pEntry is an entry for the forwarding information base that contains information on which interface has to be used in order to forward a packet towards
	 * the destination
	 */
	public void addForwardingentry(FIBEntry pEntry)
	{
		if(mForwardingEntries == null) {
			mForwardingEntries = new LinkedList<FIBEntry>();
			mForwardingEntries.add(pEntry);
		} else {
			if(!mForwardingEntries.contains(pEntry)) {
				mForwardingEntries.add(pEntry);
			}
		}
	}
	
	public String toString()
	{
		String tString = new String();
		tString += this.getClass().getSimpleName() + ":" + mHRMID;
		if(mForwardingEntries != null) {
			for(FIBEntry tEntry : mForwardingEntries) {
				tString += "\n" + tEntry.toString();
			}
		}
		return tString;  
	}
	
	/**
	 * 
	 * This class contains detailed information on how to reach a given address from the node that receives this packet
	 */
	public class FIBEntry implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2270983044012996054L;
		private HRMID mDestination;
		private HRMName mNextHop;
		private ClusterDummy mNextCluster;
		private ClusterDummy mFartestClusterInDirection;
		private boolean mWriteProtected;
		private HierarchicalSignature mSignature;
		private LinkedList<RoutingServiceLinkVector> mRoutingVectors;
		private int mBorderIdentification = 0;
		
		/**
		 * 
		 * @param pDestination: destination that can be reached with this entry
		 * @param pNextHop the next hop that has to taken in order to reach that destination
		 * @param pNextCluster as the cluster that is forwarding that entry
		 * @param pSignature is the signature of the router that produced this entry
		 */
		public FIBEntry(HRMID pDestination, HRMName pNextHop, ClusterDummy pNextCluster, HierarchicalSignature pSignature)
		{
			mNextHop = pNextHop;
			mDestination = pDestination;
			mNextCluster = pNextCluster;
			mSignature = pSignature;
		}
		
		/**
		 * 
		 * @param pDummy is the cluster in direction to the target that is still known to the node due to the chosen radius
		 */
		public void setFarthestClusterInDirection(ClusterDummy pDummy)
		{
			mFartestClusterInDirection = pDummy;
		}
		
		/**
		 * 
		 * @return the cluster in direction to the target that is still known to the node due to the chosen radius
		 */
		public ClusterDummy getFarthestClusterInDirection()
		{
			return mFartestClusterInDirection;
		}
		
		/**
		 * 
		 * @return signature of the coordinator that created this FIB entry
		 */
		public HierarchicalSignature getSignature()
		{
			return mSignature;
		}
		
		public boolean equals(Object pObj)
		{
			if(pObj instanceof FIBEntry) {
				return mDestination.equals(((FIBEntry)pObj).getDestination());
			} else if(pObj instanceof HRMID) {
				return mDestination.equals((pObj));
			}
			return false;
		}
		
		/**
		 * 
		 * @return destination this object provides information regarding the route
		 */
		public HRMID getDestination()
		{
			return mDestination;
		}
		
		/**
		 * 
		 * @return physical name of the next hop
		 */
		public HRMName getNextHop()
		{
			return mNextHop;
		}
		
		/**
		 * 
		 * @param pNextCluster is the supernode that represents the "outgoing interface" to forward the packet towards it target
		 */
		public void setNextCluster(ClusterDummy pNextCluster)
		{
			mNextCluster = pNextCluster;
		}
		
		/**
		 * 
		 * @return next cluster hop that forwards the packet in direction of the destination
		 */
		public ClusterDummy getNextCluster()
		{
			return mNextCluster;
		}
		
		public String toString()
		{
			return this.getClass().getSimpleName() + ":FROM(" + mSignature + ")DEST(" + mDestination + ")VIA(" + mNextHop + ")CLUSTER(" + mNextCluster + ")" + (mRoutingVectors != null ? "VECTORS(" + mRoutingVectors + ")" : "");
		}
		
		/**
		 * 
		 * @param pSignature of the coordinator to verify the validty and the responsibility
		 */
		public void setSignature(HierarchicalSignature pSignature)
		{
			mSignature = pSignature;
		}
		
		/**
		 * 
		 * @param pLink routing vector to reach the target
		 */
		public void addRoutingVector(RoutingServiceLinkVector pLink)
		{
			if(mRoutingVectors == null) {
				mRoutingVectors = new LinkedList<RoutingServiceLinkVector>();
			}
			mRoutingVectors.add(pLink);
		}
		
		/**
		 * 
		 * @param pVectors set a list of routing vectors to reach the target
		 */
		public void setRoutingVectors(LinkedList<RoutingServiceLinkVector> pVectors)
		{
			mRoutingVectors = pVectors;
		}
		
		/**
		 * 
		 * @return route to the target
		 */
		public LinkedList<RoutingServiceLinkVector> getRouteToTarget()
		{
			return mRoutingVectors;
		}

		/**
		 * 
		 * @param pIdentity as border node announcements have large random numbers to identify them, the following allows mapping announcements to forwarding information
		 */
		public void setBorderIdentification(int pIdentity)
		{
			mBorderIdentification = pIdentity;
		}
		
		/**
		 * 
		 * @return as border node announcements have large random numbers to identify them, the following allows mapping announcements to forwarding information
		 */
		public int getBorderIdentification()
		{
			return mBorderIdentification;
		}
	}
	
	/**
	 * TopologyEnvelopes are compared by their hierarchical identifier
	 */
	@Override
	public boolean equals(Object pObj)
	{
		if(pObj instanceof TopologyEnvelope) {
			return mHRMID.equals(((TopologyEnvelope)pObj).getHRMID());
		}
		return false;
	}
}