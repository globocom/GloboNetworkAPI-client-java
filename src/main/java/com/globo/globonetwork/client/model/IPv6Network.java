/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.globo.globonetwork.client.model;

import com.google.api.client.util.Key;

public class IPv6Network extends Network {

    @Key
    private String block1;

    @Key
    private String block2;

    @Key
    private String block3;

    @Key
    private String block4;

    @Key
    private String block5;

    @Key
    private String block6;

    @Key
    private String block7;

    @Key
    private String block8;

    @Key
    private String mask1;

    @Key
    private String mask2;

    @Key
    private String mask3;

    @Key
    private String mask4;

    @Key
    private String mask5;

    @Key
    private String mask6;

    @Key
    private String mask7;

    @Key
    private String mask8;

    @Override
    public String getNetworkAddressAsString() {
        return String.format("%s:%s:%s:%s:%s:%s:%s:%s", getBlock1(), getBlock2(), getBlock3(), getBlock4(), getBlock5(), getBlock6(), getBlock7(), getBlock8());
    }

    @Override
    public String getMaskAsString() {
        return String.format("%s:%s:%s:%s:%s:%s:%s:%s", getMask1(), getMask2(), getMask3(), getMask4(), getMask5(), getMask6(), getMask7(), getMask8());
    }

    public IPv6Network() {
        super.name = "redeipv6";
    }

    public String getBlock1() {
        return block1;
    }

    public void setBlock1(String block1) {
        this.block1 = block1;
    }

    public String getBlock2() {
        return block2;
    }

    public void setBlock2(String block2) {
        this.block2 = block2;
    }

    public String getBlock3() {
        return block3;
    }

    public void setBlock3(String block3) {
        this.block3 = block3;
    }

    public String getBlock4() {
        return block4;
    }

    public void setBlock4(String block4) {
        this.block4 = block4;
    }

    public String getBlock5() {
        return block5;
    }

    public void setBlock5(String block5) {
        this.block5 = block5;
    }

    public String getBlock6() {
        return block6;
    }

    public void setBlock6(String block6) {
        this.block6 = block6;
    }

    public String getBlock7() {
        return block7;
    }

    public void setBlock7(String block7) {
        this.block7 = block7;
    }

    public String getBlock8() {
        return block8;
    }

    public void setBlock8(String block8) {
        this.block8 = block8;
    }

    public String getMask1() {
        return mask1;
    }

    public void setMask1(String mask1) {
        this.mask1 = mask1;
    }

    public String getMask2() {
        return mask2;
    }

    public void setMask2(String mask2) {
        this.mask2 = mask2;
    }

    public String getMask3() {
        return mask3;
    }

    public void setMask3(String mask3) {
        this.mask3 = mask3;
    }

    public String getMask4() {
        return mask4;
    }

    public void setMask4(String mask4) {
        this.mask4 = mask4;
    }

    public String getMask5() {
        return mask5;
    }

    public void setMask5(String mask5) {
        this.mask5 = mask5;
    }

    public String getMask6() {
        return mask6;
    }

    public void setMask6(String mask6) {
        this.mask6 = mask6;
    }

    public String getMask7() {
        return mask7;
    }

    public void setMask7(String mask7) {
        this.mask7 = mask7;
    }

    public String getMask8() {
        return mask8;
    }

    public void setMask8(String mask8) {
        this.mask8 = mask8;
    }

}
